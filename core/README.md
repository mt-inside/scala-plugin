pushd plugins && sbt package && popd
cd core && sbt run
