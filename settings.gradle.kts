rootProject.name = "any-community"

// app
include("app:mvc-api")
include("app:consumer")

// util
include("util:faker")
include("util:jbcrypt")
include("util:jwt")
include("util:logger")
include("util:object-mapper")
include("util:custom-page")

// definition
include("definition")

// infra
include("infra:mysql")
include("infra:redis")
include("infra:kafka")

// domain
include("core:domain:shared")
include("core:domain:post")
include("core:domain:user")
include("core:domain:user_feed")
include("core:domain:follow")
include("core:domain:comment")
include("core:domain:like")
include("core:domain:point")

// usecase
include("core:usecase")
