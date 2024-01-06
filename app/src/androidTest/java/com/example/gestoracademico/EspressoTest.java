package com.example.gestoracademico;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.gestoracademico.ui.NavigationDrawer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.contrib.DrawerActions;

import java.time.LocalDate;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    // Regla para lanzar la actividad principal antes de las pruebas
    @Rule
    public ActivityTestRule<NavigationDrawer> mActivityRule = new ActivityTestRule<>(NavigationDrawer.class);

    @Test
    public void createTask_Base() {
       //Click boton crear tarea
        onView(withId(R.id.fab)).perform(click());

        //Rellenamos el formulario de la tarea
        onView(withId(R.id.editTextTextMultiLine)).perform(typeText("Texto de prueba"));
        onView(withId(R.id.editTextDate2)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.editTextDate2)).perform(closeSoftKeyboard());

        // Creamos la tarea
        onView(withId(R.id.btGuardar)).perform(click());

        //Comprobamos que se ha creado y aparece en la lista de tareas
        onView(withText("Texto de prueba")).check(matches(isDisplayed()));


    }

    @Test
    public void deleteTaskOnCalendary() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_navigation_drawer),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTextMultiLine),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_navigation_drawer),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Prueba Calendario"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextDate2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_navigation_drawer),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btGuardar), withText("Guardar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_navigation_drawer),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_gallery),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.eliminarTarea),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recyclerCalendar),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        //Comprobamos que se ha borrado la tarea
        onView(withText("Prueba Calendario")).check(matches(not(isDisplayed())));

    }

    @Test
    public void createPDF_Base(){
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_slideshow),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextCategoria),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("SEW"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextCategoria), withText("SEW"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                0)));
        appCompatEditText2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextTitle),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("JavaScript"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextContent),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextContent),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        appCompatEditText5.perform(scrollTo(), replaceText("Lenguaje interpretado, dialecto de ECMAScript, orientado a obetos basado end prototipos , imperative, debilmente tipado y dinamico"), closeSoftKeyboard());

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_navigation_drawer),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
