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

![1-DTM]()

```promql
count without(job)(http_requests_total){status="401"}
```

- Prometheus server feature

![feature]()


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

![summeries]()

In instrumentation, specify which quantile you want to calculate along with error

![construction]()

And then track a specific value

```promql
requestDuration.Observe(2.3)
```

Summery exposition

![exposition]()

Invalid quantile aggregation
```promql
avg by(job) (http_request_duration_seconds{quantile="0.9"})
```

#### Histogram

use for aggrigaton - samples observations 

(usually request durations or response sizes) and counts them into configurable buckets.

tracks distributions — not just averages.

![histogram]()

Like summeries allow you to track the distributions of the set of numeric values, but instead of printed pre-computed quantiles, it count the input value into a set of ranged packet

Qumulitive: each packet also contains the counts of the previous, lower range bucket

![qumulitive]()


You have to set up a bucket ranges to the constructore

![construction]()

Ad then you can observe value

```promql
requestDuration.Observe(2.3)
```
In the exposition format each histogram bucket is exposed as a single counter series 

![exposition]()

Quntailes from histograms

```promql
histogram_quatile(
  <target_quantile>,
  <histogram>
)
```

Since the buckets are Counters, you always wrap the rate or increase around histogram bucket

![bcket]()

Aggrigate between instances

![aggrigation]()

Average

![average]()


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











_ Last udate : _
