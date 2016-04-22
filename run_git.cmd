git fetch origin master
git reset --hard FETCH_HEAD
git clean -df

mvn -DskipTests=true clean install spring-boot:run
