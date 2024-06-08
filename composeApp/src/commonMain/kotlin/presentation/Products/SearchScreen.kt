package presentation.Products

/*
@Composable
fun SearchScreen(productCategories: ArrayList<ProductCategory>, productResourceListEnvelopeViewModel: ResourceListEnvelopeViewModel<Product>,
                  uiStateViewModel: UIStateViewModel, productPresenter: ProductPresenter, mainViewModel: MainViewModel
) {

    val productUIState = uiStateViewModel.uiData.collectAsState()

    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (productUIState.value.loadingVisible) {
            //Content Loading
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IndeterminateCircularProgressBar()
            }
        } else if (productUIState.value.errorOccurred) {
            // Error Occurred display reload
        } else if (productUIState.value.contentVisible) {

            val columnModifier = Modifier
                .padding(top = 5.dp)
                .fillMaxHeight()
                .fillMaxWidth()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Row(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        var showProductDetailBottomSheet by remember { mutableStateOf(false) }
                        if (showProductDetailBottomSheet) {
                            ProductDetailBottomSheet(Product()) {
                                showProductDetailBottomSheet = false
                            }
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).fillMaxHeight(),
                            contentPadding = PaddingValues(6.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            items(10) {
                                ProductItem(Product(),onProductClickListener = {
                                    showProductDetailBottomSheet = true
                                })
                            }
                        }
                    }
                }
            }
        }

    }
}*/
