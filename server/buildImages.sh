cd producer
./buildImage.sh &
cd ..

cd consumer
./buildImage.sh &
cd ..

cd rest
./buildImage.sh &
cd ..

cd rest_server
./buildImage.sh &
cd ..