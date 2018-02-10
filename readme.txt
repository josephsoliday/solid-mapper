TODO:

1. Improve FieldMapper to use converters and replace CustomFieldMapper with FieldMapper (same basic logic except for pre-loading the list of mappings).
2. Improve error handling by catching specific exceptions and throwing more specific runtime exceptions.
3. Cleanup code and move to Java 8 streaming.
4. Create Spring enabled mapper library module (i.e. solid-mapper-spring/solid-spring-mapper) that can be easily used within a Spring Boot app. See https://spring.io/guides/gs/multi-module/ for more details.
5. Add Library to Central Repository:
https://github.com/Hervian/lambda-factory/issues/4
http://central.sonatype.org/pages/requirements.html
https://blog.idrsolutions.com/2015/06/how-to-upload-your-java-artifact-to-maven-central/
https://maven.apache.org/guides/mini/guide-central-repository-upload.html
6. Write examples for using Mapper/Spring versions.
7. Create Website for mapper library.