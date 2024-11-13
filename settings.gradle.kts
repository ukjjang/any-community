rootProject.name = "toy"

include("mvc-api")
include("consumer")

// common-util
include("common:util:faker")
include("common:util:jbcrypt")
include("common:util:jwt")
include("common:util:logger")
include("common:util:domain-helper")
include("common:util:object-mapper")
include("common:util:custom-page")

// common-define
include("common:value")

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
include("domain:point")

// application
include("application")
