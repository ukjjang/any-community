rootProject.name = "toy"

include("mvc-api")
include("consumer")

// util
include("util:faker")
include("util:jbcrypt")
include("util:jwt")
include("util:logger")
include("util:domain-helper")
include("util:object-mapper")

// infra
include("infra:rdb")
include("infra:redis")
include("infra:kafka")

// domain
include("domain:post")
include("domain:user")
include("domain:follow")
include("domain:comment")
include("domain:like")

// application
include("application")
