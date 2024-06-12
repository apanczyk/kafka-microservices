# ISTIO Injection enabled on default namespace
kubectl label namespace default istio-injection=enabled

# Download kafka from strimzi
kubectl apply -f 'https://strimzi.io/install/latest?namespace=default'

# Download strimzi kafka persistent
kubectl apply -f https://strimzi.io/examples/latest/kafka/kafka-persistent-single.yaml

# Wait until kafka up
kubectl wait kafka/my-cluster --for=condition=Ready --timeout=300s

# Setup ui
kubectl apply -f ui/.
