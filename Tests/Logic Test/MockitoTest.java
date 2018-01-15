package com.example.matan.library;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    private static final String FAKE_STRING = "2131427389";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.login))
                .thenReturn(FAKE_STRING);
        MainActivity myObjectUnderTest = new MainActivity();

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getLoginString();

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }
}