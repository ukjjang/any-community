rootProject.name = "toy"

include("external-api")

// util
include("util:faker")
include("util:jbcrypt")
include("util:jwt")

// infra
include("infra:rdb")

// domain
include("domain:post")
include("domain:user")

// application
include("application")
