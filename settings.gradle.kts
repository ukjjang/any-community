rootProject.name = "toy"

include("external-api")

// util
include("util:faker")
include("util:jbcrypt")
include("util:jwt")
include("util:logger")
include("util:domain-helper")

// infra
include("infra:rdb")
include("infra:redis")

// domain
include("domain:post")
include("domain:user")
include("domain:follow")

// application
include("application")
