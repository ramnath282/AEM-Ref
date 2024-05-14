package com.mattel.ag.ecomm.core.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class SharedSearchModelTest {

    @InjectMocks
    private SharedSearchModel sharedSearchModel;
    @Mock
    private PropertyReaderUtils propertyReaderUtils;

    private static final String SNP_ACCOUNT_URL = "https://stage-sp1004f984.guided.ss-omtrdc.net/?index=prod";
    private static final String TARGET_SNP_URL = "https://stage-sp1004f984.guided.ss-omtrdc.net/?index=prod";
    private static final String SNP_PARAM = "&do=json_sayt&";
    private static final String TYPE_AHEAD_ACCOUNT_URL = "https://content.atomz.com";
    private static final String SEARCH_TARGET = "searchTarget";
    private static final String TOP_RESULT = "topResult";
    private static final String RELATED_RESULT = "related";
    private static final String POPULAR_RESULT = "popular";
    private static final String ARTICLE = "article";
    private static final String CATEGORIES = "categories";
    private static final String CHARACTER_LIMIT = "characterLimit";
    private static final String SUGGESTION_LIMIT = "suggestionLimit";

    @Before
    public void setup() throws Exception {
        MemberModifier.field(SharedSearchModel.class, "propertyReaderUtils").set(sharedSearchModel,
                propertyReaderUtils);
        MemberModifier.field(SharedSearchModel.class, SEARCH_TARGET).set(sharedSearchModel, SEARCH_TARGET);
        MemberModifier.field(SharedSearchModel.class, TOP_RESULT).set(sharedSearchModel, TOP_RESULT);
        MemberModifier.field(SharedSearchModel.class, RELATED_RESULT).set(sharedSearchModel, RELATED_RESULT);
        MemberModifier.field(SharedSearchModel.class, POPULAR_RESULT).set(sharedSearchModel, POPULAR_RESULT);
        MemberModifier.field(SharedSearchModel.class, ARTICLE).set(sharedSearchModel, ARTICLE);
        MemberModifier.field(SharedSearchModel.class, CATEGORIES).set(sharedSearchModel, CATEGORIES);
        MemberModifier.field(SharedSearchModel.class, CHARACTER_LIMIT).set(sharedSearchModel, CHARACTER_LIMIT);
        MemberModifier.field(SharedSearchModel.class, SUGGESTION_LIMIT).set(sharedSearchModel, SUGGESTION_LIMIT);
    }

    @Test
    public void testSharedSearchModel() {

        Mockito.when(propertyReaderUtils.getSnpAccountURLs()).thenReturn(SNP_ACCOUNT_URL);
        Mockito.when(propertyReaderUtils.getTypeAheadAccountURLs()).thenReturn(TYPE_AHEAD_ACCOUNT_URL);
        Mockito.when(propertyReaderUtils.getSnpParams()).thenReturn(SNP_PARAM);
        Mockito.when(propertyReaderUtils.getTargetSnpUrl()).thenReturn(TARGET_SNP_URL);

        sharedSearchModel.init();

        Assert.assertEquals(SNP_ACCOUNT_URL, sharedSearchModel.getSnpAccountUrl());
        Assert.assertEquals(TYPE_AHEAD_ACCOUNT_URL, sharedSearchModel.getTypeAheadAccountUrl());
        Assert.assertEquals(SNP_PARAM, sharedSearchModel.getSnpParams());
        Assert.assertEquals(TARGET_SNP_URL, sharedSearchModel.getTargetSnpUrl());
        Assert.assertNotNull(sharedSearchModel.getPropertyReaderUtils());

    }

    @Test
    public void testSharedSearchModelWithParameters() {
        sharedSearchModel.setSnpAccountUrl(SNP_ACCOUNT_URL);
        sharedSearchModel.setTypeAheadAccountUrl(TYPE_AHEAD_ACCOUNT_URL);
        Assert.assertEquals(SEARCH_TARGET, sharedSearchModel.getSearchTarget());
        Assert.assertEquals(TOP_RESULT, sharedSearchModel.getTopResult());
        Assert.assertEquals(RELATED_RESULT, sharedSearchModel.getRelated());
        Assert.assertEquals(POPULAR_RESULT, sharedSearchModel.getPopular());
        Assert.assertEquals(ARTICLE, sharedSearchModel.getArticle());
        Assert.assertEquals(CATEGORIES, sharedSearchModel.getCategories());
        Assert.assertEquals(CHARACTER_LIMIT, sharedSearchModel.getCharacterLimit());
        Assert.assertEquals(SUGGESTION_LIMIT, sharedSearchModel.getSuggestionLimit());

    }

}
