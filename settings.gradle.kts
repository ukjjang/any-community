rootProject.name = "toy"

include("external-api")

// util
include("util:faker")
include("util:jbcrypt")

// infra
include("infra:rdb")

// domain
include("domain:post")

// application
include("application")
