FROM airhacks/glassfish
COPY ./target/events.war ${DEPLOYMENT_DIR}
