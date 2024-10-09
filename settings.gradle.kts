rootProject.name = "toy"

// infra
include("infra:rdb")

// domain
include("domain:post")

// application
include("application")

// apps
include("apps:external-api")
