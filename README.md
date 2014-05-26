# DiagOOM 

Dataflow-centric memory analysis for *diagnosing the causes of OOM in MapReduce jobs*

## Requriements
- [Our enhanced Hadoop-1.2.0](https://github.com/JerryLead/hadoop-1.2.0-enhanced)
- [Our enhanced Eclipse MAT](https://github.com/JerryLead/enhanced-Eclipse-MAT)

## Usage
1. Run Hadoop jobs.
2. When OOM occurs in a job, please record the JobId and failed TaskId.
3. Run `profile.profiler.SingleJobProfiler.main()` to collect the logs, dataflow counters, and JVM heap usage.
4. If no user code is running, go to 7.
5. Run `dataflow.model.job.JobDataflowModelBuilder.main()` to get additional dump configurations.
6. Rerun the job with the configurations.
7. collect the  'heap dump@OOM' and 'heap dump@record(i)' from the cluster.
8. Run our `enhanced Eclipse MAT` to get the 'Framework objects@OOM' and  'User objects@record(i)'
9. Run `object.model.job.DumpedObjectsAnalyzer.OOMAnalyzer.main()` to get the diagnosis report.

