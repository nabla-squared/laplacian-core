main() {
  if [ -z $SKIP_GENERATION ]
  then
    generate
  fi
  publish_local 'model'
  publish_domain_model_plugin
}

publish_domain_model_plugin() {
  (cd $DEST_DIR/domain-model-plugin
    $GRADLE publish
  )
}
