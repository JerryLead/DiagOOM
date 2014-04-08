package html.parser.task;

import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import profile.mapper.MapperInfo;
import profile.reducer.ReducerInfo;
import html.util.HtmlFetcher;

public class TaskLogParser {

    public static void parseMapperLog(String logLink, MapperInfo mapper) {
	Document mapLogs = HtmlFetcher.getHtml(logLink);

	Element syslogPre = mapLogs.getElementsByTag("pre").last();
	String syslog[] = syslogPre.text().split("\\n");

	int i;
	
	for (i = 0; i < syslog.length; i++) {
	    if (syslog[i].contains("[HeapDump]")) {
		String heapdump = syslog[i].substring(syslog[i].lastIndexOf(' ') + 1);
		mapper.addHeapDump(heapdump);
	    }
	}

	for (i = 0; i < syslog.length; i++) {
	    /*
	     * if (syslog[i].contains("io.sort.mb")) { int ioSortMb =
	     * Integer.parseInt(syslog[i].substring(syslog[i].lastIndexOf('=') +
	     * 2)); // 500 mapper.getBuffer().setIoSortMB(ioSortMb); }
	     */
	    if (syslog[i].contains("Processing split")) {
		String splitBytes = syslog[i].substring(syslog[i].lastIndexOf(':') + 2);
		mapper.getInput().setSplitBytes(Long.parseLong(splitBytes));
	    }

	    else if (syslog[i].contains("data buffer")) {
		String[] dataBuffer = syslog[i].substring(
			syslog[i].lastIndexOf('=') + 2).split("/");

		long softBufferLimit = Long.parseLong(dataBuffer[0]); // 318767104
		long kvbufferBytes = Long.parseLong(dataBuffer[1]); // 398458880

		mapper.getBuffer().setDataBuffer(softBufferLimit, kvbufferBytes);
		
		
	    }

	    else if (syslog[i].contains("record buffer")) {
		String[] recordBuffer = syslog[i].substring(
			syslog[i].lastIndexOf('=') + 2).split("/");

		long softRecordLimit = Long.parseLong(recordBuffer[0]); // 1048576
		long kvoffsetsLen = Long.parseLong(recordBuffer[1]); // 1310720

		mapper.getBuffer().setRecordBuffer(softRecordLimit,
			kvoffsetsLen);
		
		i++;
		break;
	    }
	    

	    else if (syslog[i].contains("[map() begins]")) {
		break;
	    }
	    
	   
	}

	
	
	String reason = "";
	int spillid = 0;

	for (; i < syslog.length; i++) {

	    if (syslog[i].contains("[map() begins]")) {
		mapper.setRunningPhase("map");
		mapper.setIsMapRunning(true);
	    }

	    else if (syslog[i].contains("Spilling map output")) {
		int loc = syslog[i].indexOf('=') + 2;
		reason = syslog[i].substring(loc, syslog[i].indexOf(',', loc));
		long[] numbers = extractLongNumber(syslog[i].substring(loc), 2);
		
		mapper.getSpill().addBeforeSpillItem(reason, numbers[0], numbers[1]);
		
		mapper.setRunningPhase("spill");
	
		
	    }

	    else if (syslog[i].contains("Starting flush of map output")) {
		reason = "flush";
		long[] numbers = extractLongNumber(syslog[i], 2);
		
		mapper.getSpill().addBeforeSpillItem(reason, numbers[0], numbers[1]);
	
		mapper.setRunningPhase("spill");
	    }

	    else if (syslog[i].contains("Finished spill")) {
		int spillLoc = syslog[i].indexOf("spill") + 6;
		int spillId = Integer.parseInt(syslog[i].substring(spillLoc,
			syslog[i].indexOf(" ", spillLoc))); // 0

		assert (spillid == spillId);

		String valuesStr = syslog[i].substring(
			syslog[i].indexOf('<') + 1, syslog[i].lastIndexOf('>'));

		if (syslog[i].contains("without combine")) {
		    long[] values = extractLongNumber(valuesStr, 4);
		    long Records = values[0];// 1310720
		    long BytesBeforeSpill = values[1]; // 13585067
		    long RawLength = values[2]; // 2492902
		    long CompressedLength = values[3]; // 2492966
		    // System.out.println("[without combine] " + "Records = " +
		    // Records + ", BytesBeforeSpill = " + BytesBeforeSpill
		    // + ", RawLength = " + RawLength + ", CompressedLength = "
		    // + CompressedLength);

		    mapper.getSpill().addAfterSpillItem(spillid, false, Records, RawLength,
			    CompressedLength);
		    

		} else {
		    long[] values = extractLongNumber(valuesStr, 5);
		    long RecordsBeforeCombine = values[0]; // 1310720
		    long BytesBeforeSpill = values[1]; // 13585067
		    long RecordAfterCombine = values[2]; // 161552
		    long RawLength = values[3]; // 2492902
		    long CompressedLength = values[4]; // 2492966

		    // System.out.println("[with combine] " +
		    // "RecordsBeforeCombine = " + RecordsBeforeCombine +
		    // ", BytesBeforeSpill = "
		    // + BytesBeforeSpill + ", RecordAfterCombine = " +
		    // RecordAfterCombine + ", RawLength = " + RawLength
		    // + ", CompressedLength = " + CompressedLength);
		    // System.out.println();
		    mapper.getSpill().addAfterSpillItem(spillid, true, RecordAfterCombine, RawLength, CompressedLength);
		    
		  
		}
		
		++spillid;
	    }

	    else if (syslog[i].contains("[map() ends]")) {
		mapper.setIsMapRunning(false);
	    }

	    else if (syslog[i].contains("PartInfos")) {
		mapper.setRunningPhase("merge");

		int partitionId = Integer.parseInt(syslog[i].substring(
			syslog[i].indexOf("Partition") + 10,
			syslog[i].lastIndexOf(']')));

		String valueString = syslog[i].substring(
			syslog[i].lastIndexOf('<') + 1,
			syslog[i].lastIndexOf('>'));
		long[] values = extractLongNumber(valueString, 5);

		long RecordsBeforeCombine = values[0];
		long RawLengthBeforeMerge = values[1];
		long RecordsAfterCombine = values[2];
		long RawLength = values[3];
		long CompressedLength = values[4];

		mapper.getMerge().addBeforeMergeItem(partitionId, false, 1, RecordsBeforeCombine,
			RawLengthBeforeMerge, RawLengthBeforeMerge);
		mapper.getMerge().addAfterMergeItem(partitionId,
			RecordsAfterCombine, RawLength,
			CompressedLength);
		

	    }

	    else if (syslog[i].contains("[BeforeMerge]")) {
		mapper.setRunningPhase("merge");

		break;
	    }

	    else if (syslog[i].contains("done. And is in the process of commiting")) {
		mapper.setRunningPhase("over");
			
		break;
	    }
	} // end for (; i < syslog.length; i++)

	for (; i < syslog.length; i++) {
	    if (syslog[i].contains("[BeforeMerge]")) {

		int partitionIdStart = Integer.parseInt(syslog[i].substring(
			syslog[i].indexOf("Partition") + 10,
			syslog[i].lastIndexOf(']')));
		String valueStr = syslog[i].substring(
			syslog[i].lastIndexOf('<') + 1,
			syslog[i].lastIndexOf('>'));

		String combineStr = syslog[i].substring(syslog[i].indexOf('=') + 2, syslog[i].indexOf('<') - 1);
		boolean hasCombine = Boolean.parseBoolean(combineStr);
		long[] values = extractLongNumber(valueStr, 4);
		int SegmentsNum = (int) values[0]; // 32
		long records = values[1];
		long RawLength = values[2]; // 4852329
		long CompressedLength = values[3]; // 4852457
		// System.out.println("[BeforeMerge][" + partitionIdStart + "]["
		// + startMergeTimeMS + "] SegmentsNum = " +
		// SegmentsNum + ", RawLength = " + RawLength +
		// ", CompressedLength = " + CompressedLength);
		mapper.getMerge().addBeforeMergeItem(partitionIdStart, hasCombine,
			SegmentsNum, records, RawLength, CompressedLength);
		
		for (int j = i + 1; j < syslog.length; j++) {
		    if (syslog[j].contains("AfterMergeAndCombine")) {

			int partitionIdEnd = Integer.parseInt(syslog[j]
				.substring(syslog[j].indexOf("Partition") + 10,
					syslog[j].lastIndexOf(']')));
			assert (partitionIdStart == partitionIdEnd);
			String valueString = syslog[j].substring(
				syslog[j].lastIndexOf('<') + 1,
				syslog[j].lastIndexOf('>'));
			values = extractLongNumber(valueString, 4);

			long RecordsBeforeMerge = values[0]; // 316038
			long RecordsAfterMerge = values[1]; // 120636
			long RawLengthEnd = values[2]; // 1986498
			long CompressedLengthEnd = values[3]; // 1986502

			mapper.getMerge().addAfterMergeItem(partitionIdEnd,
				RecordsAfterMerge,
				RawLengthEnd, CompressedLengthEnd);
			// System.out.println("[AfterMergeAndCombine][" +
			// partitionIdEnd + "][" + stopMergeTimeMS +
			// "] RecordsBeforeCombine = "
			// + RecordsBeforeCombine + ", RecordsAfterCombine = " +
			// RecordsAfterCombine + ", RawLength = " + RawLengthEnd
			// + ", CompressedLength = " + CompressedLengthEnd);
			// System.out.println();
			
			
			i = j;
			break;
		    }
		}
	    }

	    else if (syslog[i].contains("done. And is in the process of commiting")) {
		mapper.setRunningPhase("over");
		
		break;
	    }
	}

    }

    public static void parseReducerLog(String logLink, ReducerInfo reducer) {
	Document reduceLogs = HtmlFetcher.getHtml(logLink);
	Element syslogPre = reduceLogs.getElementsByTag("pre").last();
	String syslog[] = syslogPre.text().split("\\n");

	int i;
	
	for (i = 0; i < syslog.length; i++) {
	    if (syslog[i].contains("[HeapDump]")) {
		String heapdump = syslog[i].substring(syslog[i].lastIndexOf(' ') + 1);
		reducer.addHeapDump(heapdump);
	    }
	}
	
	for (i = 0; i < syslog.length; i++) {
	    if (syslog[i].contains("ShuffleRamManager:")) { // ShuffleRamManager:
							    // MemoryLimit=652482944,
							    // MaxSingleShuffleLimit=163120736

		int start = syslog[i].indexOf('=') + 1; // 652482944,
							// MaxSingleShuffleLimit=163120736
		int end = syslog[i].indexOf(',', start); // ,
							 // MaxSingleShuffleLimit=163120736
		long memoryLimit = Long.parseLong(syslog[i].substring(start,
			end));

		start = syslog[i].indexOf('=', end) + 1; // 163120736
		long maxSingleShuffleLimit = Long.parseLong(syslog[i]
			.substring(start));

		reducer.getShuffleBuffer().set(memoryLimit,
			maxSingleShuffleLimit);
		
		reducer.setRunningPhase("shuffle");
		
		i++;
		break;
	    }
	}

	for (; i < syslog.length; i++) {
	    // 2012-10-13 11:57:54,795 INFO org.apache.hadoop.mapred.ReduceTask:
	    // Shuffling 34476716 bytes (5047132 raw bytes) into RAM from
	    // attempt_201210131136_0002_m_000000_0
	    if (syslog[i].contains("Shuffling")) {
		int start = syslog[i].indexOf("Shuffling") + 10;
		int end = syslog[i].indexOf(' ', start);
		long decompressedLen = Long.parseLong(syslog[i].substring(
			start, end)); // 34476716

		start = syslog[i].indexOf('(', end) + 1;
		end = syslog[i].indexOf(' ', start);
		long compressedLen = Long.parseLong(syslog[i].substring(start,
			end)); // 5047132

		start = syslog[i].indexOf("into", end) + 5;
		end = syslog[i].indexOf(' ', start);
		String storeLoc = syslog[i].substring(start, end); // RAM

		start = syslog[i].indexOf("from", end) + 5;
		String sourceTaskId = syslog[i].substring(start); // attempt_201208242049_0014_m_000050_0

		// System.out.println("[Shuffling][" + shuffleFinishTimeMS +
		// "][" + storeLoc + "] decompressedLen = " + decompressedLen +
		// ", compressedLen = " + compressedLen);
		reducer.getShuffle().addShuffleItem(sourceTaskId, storeLoc,
			decompressedLen, compressedLen);
		
		// System.out.println(syslog[i]);
	    }
	    
	    // Read 66339361 bytes from map-output for attempt_201404061331_0008_m_000012_0
	    else if (syslog[i].contains("bytes from map-output for")) {
		int start = syslog[i].indexOf("Read") + 5;
		int end = syslog[i].indexOf("bytes", start) - 1;
		long bytes = Long.parseLong(syslog[i].substring(start, end));
		
		String sourceTaskId = syslog[i].substring(syslog[i].indexOf("for") + 4); // attempt_201208242049_0014_m_000050_0
		// System.out.println("[Shuffling][" + shuffleFinishTimeMS +
		// "][" + storeLoc + "] decompressedLen = " + decompressedLen +
		// ", compressedLen = " + compressedLen);
		reducer.getShuffle().addShuffleItem(sourceTaskId, "Disk",
			bytes, bytes);
		
	    }
	    
	    else if (syslog[i].contains("[InMemoryShuffleMerge begins]")) {
		String idStr = syslog[i].substring(syslog[i].indexOf('(') + 1, syslog[i].lastIndexOf(')'));
		String[] taskIds = idStr.split(", ");
		reducer.getMergeInShuffle().addShuffleBeforeMergeItem(taskIds);
		reducer.setInMemMergeRunning(true);

	    }

	    else if (syslog[i].contains("[InMemoryShuffleMerge]")) {

		String valueStr = syslog[i].substring(
			syslog[i].indexOf('<') + 1, syslog[i].lastIndexOf('>'));
		long[] values = extractLongNumber(valueStr, 6);

		int SegmentsNum = (int) values[0]; // 29
		long RecordsBeforeMergeAC = values[1];// 9789063
		long BytesBeforeMergeAC = values[2]; // 998484484
		long RecordsAfterCombine = values[3]; // 9789063
		long RawLength = values[4]; // 998484428
		long CompressedLength = values[5]; // 149009950

		reducer.getMergeInShuffle().addShuffleAfterMergeItem(
			SegmentsNum, RecordsBeforeMergeAC,
			BytesBeforeMergeAC, RecordsAfterCombine, RawLength,
			CompressedLength);
		reducer.setInMemMergeRunning(false);

	    }
	    
	    else if (syslog[i].contains("Interleaved on-disk merge complete")) {
		int mapOutputFilesOnDisk = Integer.parseInt(syslog[i]
			.substring(syslog[i].lastIndexOf(':') + 2,
				syslog[i].lastIndexOf("files") - 1));
		
		reducer.getShuffle().setOnDiskSegmentsAfterShuffle(
			mapOutputFilesOnDisk);
		
		
	    }
	    
	    else if (syslog[i].contains("In-memory merge complete")) {
		
		int mapOutputsFilesInMemory = Integer.parseInt(syslog[i]
			.substring(syslog[i].lastIndexOf(':') + 2,
				syslog[i].lastIndexOf("files") - 1));
		
		reducer.getShuffle().setInMemorySegmentsAfterShuffle(mapOutputsFilesInMemory);

		reducer.setRunningPhase("sort");
		i++;
		break;
	    }
	}

	// sort phase begins
	

	for (; i < syslog.length; i++) {
	    // 2012-10-13 12:09:49,453 INFO org.apache.hadoop.mapred.ReduceTask:
	    // Keeping 3 segments, 103346202 bytes in memory for intermediate,
	    // on-disk merge
	    /*
	    if (syslog[i].contains("ReduceTask: Keeping")) {
		
		int mergeSegmentsNum = Integer.parseInt(syslog[i].substring(
			syslog[i].indexOf("Keeping") + 8,
			syslog[i].indexOf("segments") - 1));
		long sizeAfterMerge = Long.parseLong(syslog[i].substring(
			syslog[i].indexOf("segments,") + 10,
			syslog[i].lastIndexOf("bytes") - 1));
		// System.out.println("[Keeping in memory][" +
		// stopInMemorySortMergeTimeMS + "] SegmentsNum = "
		// + mergeSegmentsNum + ", sizeAfterMerge = " + sizeAfterMerge);

		reducer.getSort().setInMemorySortMergeItem(
			 mergeSegmentsNum, 0,
			sizeAfterMerge, sizeAfterMerge, sizeAfterMerge);
		i++;
		break;
	    }
	    */
	    // 2012-10-13 11:42:21,960 INFO org.apache.hadoop.mapred.ReduceTask:
	    // [InMemorySortMerge]<SegmentsNum = 38, Records = 5083386,
	    // BytesBeforeMerge = 82317689,
	    // RawLength = 82317615, CompressedLength = 82317619>
	    if (syslog[i].contains("[InMemorySortMerge]")) {
		
		String valueStr = syslog[i].substring(
			syslog[i].indexOf('<') + 1, syslog[i].lastIndexOf('>'));

		long[] values = extractLongNumber(valueStr, 5);
		int SegmentsNum = (int) values[0]; // 38
		long Records = values[1]; // 5083386
		long BytesBeforeMerge = values[2]; // 82317689
		long RawLength = values[3]; // 82317615
		long CompressedLength = values[4]; // 82317619

		reducer.getSort().setInMemorySortMerge(
			SegmentsNum, Records,
			BytesBeforeMerge, RawLength, CompressedLength);
		reducer.setRunningPhase("InMemorySortMerge");
	    }

	    else if (syslog[i].contains("[MixSortMerge]")) {
		
		// should be
		// String valuStr = syslog[i].substring(syslog[i].indexOf('<') +
		// 1, syslog[i].lastIndexOf('>'));
		String valueStr = syslog[i]
			.substring(syslog[i].indexOf('<') + 1);

		long[] values = extractLongNumber(valueStr, 4);
		int InMemorySegmentsNum = (int) values[0]; // 3
		long InMemorySegmentsSize = values[1]; // 103346202
		int OnDiskSegmentsNum = (int) values[2]; // 13
		long OnDiskSegmentsSize = values[3]; // 1896494449

		reducer.getSort().setMixSortMerge(
			InMemorySegmentsNum, InMemorySegmentsSize,
			OnDiskSegmentsNum, OnDiskSegmentsSize);
		
		reducer.setRunningPhase("MixSortMerge");
		
	    }
	    
	    else if (syslog[i].contains("[FinalSortMerge]")) {
		

		String valueStr = syslog[i]
			.substring(syslog[i].indexOf('<') + 1, syslog[i].lastIndexOf('>')) ;
		long[] values = extractLongNumber(valueStr, 2);

		int InMemorySegmentsNum = (int) values[0]; // inMemSegmentsNum
		long inMemBytes = values[1]; // inMemBytes
		
		String idStr = syslog[i].substring(syslog[i].indexOf('(') + 1, syslog[i].lastIndexOf(')'));
		String[] taskIds = idStr.split(", ");
		
		reducer.getSort().setFinalSortMerge(
			InMemorySegmentsNum,
			inMemBytes, taskIds);
		
		reducer.setRunningPhase("FinalSortMerge");
	    }
	    
	    else if (syslog[i].contains("[Reduce phase begins]")) {
		// System.out.println("[Reduce() begins]");
		
		reducer.setRunningPhase("reduce");
		
	    }

	    else if (syslog[i].contains("is allowed to commit now")) {
		reducer.setRunningPhase("over");
	    }
	}
	
    }

    public static long[] extractLongNumber(String line, int n) {
	long[] values = new long[n];
	Scanner scanner = new Scanner(line).useDelimiter("[^0-9]+");
	int i = 0;

	while (scanner.hasNextLong())
	    values[i++] = scanner.nextLong();
	return values;
    }

    public static void main(String[] args) {
	MapperInfo mapperInfo = new MapperInfo();
	parseMapperLog("http://slave3:50060/tasklog?attemptid=attempt_201403261726_0001_m_000000_0&all=true",
		mapperInfo);
	System.out.println(mapperInfo);

	/*
	parseReducerLog(
		"http://slave5:50060/tasklog?attemptid=attempt_201403211644_0007_r_000000_0&all=true",
		new ReducerInfo());
	*/
    }

}
