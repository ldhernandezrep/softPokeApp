object Dependencies {

    val di = listOf(
        Dependency.Hilt,
        Dependency.Kapt.Hilt,
    )

    val common = listOf(
      Dependency.Coroutines,
      Dependency.CoreKtx
    )

    val domain = listOf(
        Dependency.Paging
    ).plus(di)

    val app = listOf(
        Dependency.Paging,
        Dependency.LiveData,
        Dependency.ViewModel,
        Dependency.AppCompat,
        Dependency.ConstraintLayout,
        Dependency.Palette,
        Dependency.NavigationFragment,
        Dependency.NavigationUI,
        Dependency.Glide,
    ).plus(common).plus(di)

    val network = listOf(
        Dependency.Paging,
        Dependency.Moshi,
        Dependency.MoshiAdapter,
        Dependency.MoshiKotlin,
        Dependency.RetrofitConverter,
        Dependency.Retrofit2,
        Dependency.Retrofit2GsonAdapter,
        Dependency.Retrofit2LoggingInterceptor,
    ).plus(common).plus(di)
}