package com.ondevop.a75hard.navigation

sealed class Route(val route: String) {

    object Welcome: Route("welcome")
    object NotificationAllow : Route("notification_allow")
    object SignIn : Route("sign_in")
    object SignUp : Route("sign_up")

    object TrackerHome: Route("tracker_home")

    object Setting: Route("setting")

    object GraphTracker: Route("graph_tracker")
    object GraphAuth: Route("graph_auth")
}
