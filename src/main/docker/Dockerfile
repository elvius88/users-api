FROM core.harbor.work.py.igrupobbva/runes/base-api:0.1.0

LABEL "py.com.jaha.api.users-api.version"=@project.version@

RUN mkdir -p /usr/local/@project.artifactId@
ADD @project.build.finalName@.jar /usr/local/@project.artifactId@/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
