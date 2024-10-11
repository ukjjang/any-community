rootProject.name = "toy"

// util
include("util:faker")
include("util:jbcrypt")

// infra
include("infra:rdb")

// domain
include("domain:post")

// application
include("application")

// app
include("app:external-api")
