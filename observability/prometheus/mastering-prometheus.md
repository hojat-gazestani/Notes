# Prometheus

_Master DevOps Monitoring with Prometheus._

---

### Overview
Tutorial on [Git.ir](https://en.git.ir/udemy-master-devops-monitoring-with-prometheus/) site

Complete Prometheus Monitoring guide. Master monitoring theory, instrumentation, metrics exporters, alerting and more

Publisher:
Udemy
Instructor:
Brendon Palmer

## Course content

- Course Introduction and the Status of Prometheus
- Principles in Monitoring and How Prometheus Works
    - Breaking down the Data Shard Anatomy
- Installing and Setting up the Prometheus Server
- App Instrumentation and Metric Guidelines
    - The 4 metrics type
- Infrastructure Monitoring
- Pushing Metrics to Prometheus
- Service Discovery Techniques
- Labelling
- PromQL; The Prometheus Query Language

- metrics type
    - Counter
    - Gage
    - Histogram

## Principles in Monitoring and How Prometheus Works

### Breaking down the Data Shard Anatomy

- Dimensional data model

![dimensional-data-model](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/00-dimesional-data-model.png)


```text
test-server.1.2.3.4:80./dir.GET.404
test-server.*/dir.* 404

# Victoria metrics
metric_name{label1="value1", label2="value2"} value timestamp
http_requests_total{method="GET", status="200", instance="web-01"} 15234 1695000000
```

```bash
<aggregators><functions><metric_name><key=value, ...>
```

```promql
function: up
```

e.g:
```promql
node_cpu_seconds_total
rate(node_cpu_seconds_total)[5d]
```

![1-DTM](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/1-DTM-structure.png)

```promql
count without(job)(http_requests_total){status="401"}
```

- Prometheus server feature

![feature](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/02-features.png)


## App Instrumentation and Metric Guidelines

### The 4 metrics type

#### Counter

Value that **only increase** (except when the process restarts/resets)

```promql
*_total
http_requests_total{status="500"} 42

rate(http_requests_total[5m])   # requests per second over 5 min
                                # (e.g., 2.5 means 2.5 requests/sec)

increase(http_requests_total[5m]) # total count of increase over the last 5 minutes 
                                  #(e.g., 750 means 750 requests happened in that window)
```

#### Gage

**current value**. It can go up or down.

```promql
node_memory_usage_bytes
active_connections 1  # means there is only 1 active connection

# Directly (instant value)
node_memory_usage_bytes > 1e9          # filter
node_memory_usage_bytes / 1024^3       # convert to GB

# Over time (aggregate functions)
avg_over_time(temperature_celsius[1h])   # average temp over 1h
max_over_time(memory_usage_bytes[1h])    # peak memory in 1h
min_over_time(queue_length[1h])          # lowest queue depth

delta(cpu_temp[1h])              # change over 1h (last - first)
deriv(memory_usage[10m])         # per-second slope (rate of change)
predict_linear(memory[1h], 3600) # predict value 1h into future
changes(queue_length[1h])        # how many times it changed

# Common Gauge Patterns
100 * (node_memory_used / node_memory_total)   # Percent utilization:
deriv(process_resident_memory_bytes[10m]) > 1e6  # Detect a gauge going up fast (memory leak)
avg_over_time(cpu_usage[5m])     # Smooth out noisy gauges:

```
#### Summary

Track the distribution of request latency

Or of some other set of numberic values as a percentile or a quantile 

![summeries](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/03-summeries2.png)

In instrumentation, specify which quantile you want to calculate along with error

![construction](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/04-construction.png)

And then track a specific value

```promql
requestDuration.Observe(2.3)
```

Summery exposition

![exposition](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/03-summery-exposion.png)

Invalid quantile aggregation
```promql
avg by(job) (http_request_duration_seconds{quantile="0.9"})
```

#### Histogram

use for aggrigaton - samples observations 

(usually request durations or response sizes) and counts them into configurable buckets.

tracks distributions — not just averages.

![histogram](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/06-histograms.png)

Like summeries allow you to track the distributions of the set of numeric values, but instead of printed pre-computed quantiles, it count the input value into a set of ranged packet

Qumulitive: each packet also contains the counts of the previous, lower range bucket

![qumulitive](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/07-qumulive.png)


You have to set up a bucket ranges to the constructore

![construction](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/08-construction.png)

Ad then you can observe value

```promql
requestDuration.Observe(2.3)
```
In the exposition format each histogram bucket is exposed as a single counter series 

![exposition](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/09-exposition.png)

Quntailes from histograms

```promql
histogram_quatile(
  <target_quantile>,
  <histogram>
)
```

Since the buckets are Counters, you always wrap the rate or increase around histogram bucket

![bcket](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/10-bucket-rate.png)

Aggrigate between instances

![aggrigation](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/11-aggrigation.png)

Average

![average](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/11-average-latencies.png)


### Approaching Instrumentation

Service types

- Online-serving system
- Offline-seving system
- Batch Jobs


**Online-serving system**

Has a human or other service waing on a response

- Requests
- Error
- Duration

**Offline-serving system**

Has no response requests - usauly pipelines

- Utilization: How full the service is
- Saturation
- Errors

**Batch job**

Run on a reqular schedule

Use push gateway

- Total run duration
- Duration per stage
- Job success timestamp

### PROMQL: The PROMetheus Query Language

- Intro to Aggregation
- HTTP API
- Without and By Sum and Count
- Min and Max
- Topk, Bottomk and Count_values
- Binary Operators
- Ignoring, On
- Or, Unless, And
- Intro to Functions
- Sort and Sort_desc
- Avg, Rate and Irate
- Driv and Predict_liner

#### Intro to Aggregation

```promql
up{node="kind-worker"}
up{node!="kind-worker"}
up{node=~"kind-worker."}      # kind-worker1, kind-workder2
up{node=~"kind-worker.+"}     # kind-worker1XXX, kind-workder2XXX
up{node=~"kind-worker1|2"}    # kind-worker1, kind-workder2

{job="vmstorage"}
{job="vmstorage",value!~".+m.+"}

```
---

**e.g.1:**

The metric that I am going to filter 

![12-query](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/12-query.png)

```promql
sum without(job, cluster_name, node) (
  process_cpu_seconds_total{job!="vminsert"}
)
```

Total CPU usage per instance

![13-query-result](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/13-query-result.png)

Adding rate for 5 minute

```promql
sum without(job, cluster_name, node) (
  rate(process_cpu_seconds_total{job!="vminsert"})[5m]
)
```

![13-qeury-result-minuts](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/13-qeury-result-minuts.png)

---

**e.g.2:**

Total CPU usage of all containers combined

```promql
container_cpu_usage_seconds_total

      {
        "metric": {
          "__name__": "container_cpu_usage_seconds_total",
          "cpu": "total",
          "id": "/kubelet.slice/kubelet-kubepods.slice/kubelet-truncated
          "image": "registry.k8s.io/pause:3.10",
          "instance": "kind-control-plane",
          "job": "kubernetes-cadvisor",
          "name": "63a45c2b776de7eb0cb775b1f72c85b6833a6e1bd06e288108400d038d582f5d",
          "namespace": "local-path-storage",
          "pod": "local-path-provisioner-567f868bf9-s9wrh"
        },
        "value": [
          1789748615,
          "0.072963"

```

I have container name 

```promql
sum by (container) (
  container_cpu_usage_seconds_total
)
```

![14-sum-cont-name](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/14-sum-cont-name.png)

But I only need kube containers

```promql
container_cpu_usage_seconds_total{id=~"/kubelet.slice/.+"}
```

![15-kube-cont-only](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/15-kube-cont-only.png)

Total CPU usage for all my Kube containers per instance
The matching instance's containers have consumed a total of the provided number since the counter started not currently usage.

```promql
sum by (instance) (
  container_cpu_usage_seconds_total{id=~"/kubelet.slice/.+"}
)
```

![16-kube-cpu-per-instance](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/16-kube-cpu-per-instance.png)

For each instance, how many CPU cores were being consumed on average by these kubelet-related containers during the last 5 minutes?
The `rate` converts the cumulative counter into a CPU consumption rate.

```promql
sum by (instance) (
  rate(container_cpu_usage_seconds_total{id=~"/kubelet.slice/.+"})[5m]
)
```

![17-kube-cpu-per-instance-rate](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/17-kube-cpu-per-instance-rate.png)

Average CPU usage per node

```promql
avg by (instance) (
  rate(container_cpu_usage_seconds_total{id=~"/kubelet.slice/.+"})[5m]
)
```

![18-kube-cpu-per-node](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/18-kube-cpu-per-node.png)

And if I need average CPU usage for all node

```promql
avg( 
 sum by (instance) (
    rate(container_memory_max_usage_bytes)[5m]
  )
)
```

![19-kube-cpu-of-node](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/18-kube-cpu-per-node.png)

---

##### max, min, tok k, bottom k, count

Max CPU usage per seconds for the instance in a culster

```promeql
max(
  process_cpu_seconds_total{instance=~".+31.+", cluster_name="prometheus-test"}
)
```

![20-max-cpu-usage-instance](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/20-max-cpu-usage-instance.png)


Largest value of the metric

```promql
max(go_gc_duration_seconds_sum)
```

![21-go-grbage-collection](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/21-go-grbage-collection.png)


Top 3 go garbage collection

```promql
topk(
  3, go_gc_duration_seconds_sum
)
```

![22-top-3-go-garbage](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/22-top-3-go-garbage.png)


bottom 3 go garbage collection

```promql
bottomk(
  3, go_gc_duration_seconds_sum
)
```

![23-bottm-3-go-garbage](https://github.com/hojat-gazestani/Notes/blob/main/observability/prometheus/pics/23-bottm-3-go-garbage.png)

---

**count_values**

Imagine:

```promql
http_requests_total{service="api"}      100
http_requests_total{service="web"}      50
http_requests_total{service="worker"}   100
http_requests_total{service="db"}       50
```

How many series have each distinct value?

```promql
count_values("requests", http_requests_total)
```

result:

```
requests="100"   2
requests="50"    2
```

Because:
```text
100 → api
100 → worker

50 → web
50 → db
```
The new label requests is created from the metric value.

_ Last udate : _
