function searchWCS()
{
    getSearchString();
}
function getSearchString()
{	
    var searchString = $('#SimpleSearchForm_SearchTerm').val();
    window.location = "//www.americangirl.com/shop/SearchDisplay?categoryId=&storeId=10651&catalogId=10601&langId=-1&sType=SimpleSearch&resultCatEntryType=2&showResultsPage=true&searchSource=Q&pageView=&beginIndex=0&pageSize=36&searchTerm=" + searchString + "#facet:&productBeginIndex:0&orderBy:&pageView:grid&minPrice:&maxPrice:&pageSize:&contentPageSize:&";
    return searchString;
}