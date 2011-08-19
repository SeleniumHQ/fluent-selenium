/*
Copyright 2011 Software Freedom Conservancy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.seleniumhq.selenium.fluent;

import org.openqa.selenium.By;

public interface FluentWebDriver {

    OngoingFluentWebDriver span();
    OngoingFluentWebDriver span(By by);
    OngoingFluentWebDriver spans();
    OngoingFluentWebDriver spans(By by);

    OngoingFluentWebDriver div();
    OngoingFluentWebDriver div(By by);
    OngoingFluentWebDriver divs();
    OngoingFluentWebDriver divs(By by);

    OngoingFluentWebDriver link();
    OngoingFluentWebDriver link(By by);
    OngoingFluentWebDriver links();
    OngoingFluentWebDriver links(By by);

    OngoingFluentWebDriver input();
    OngoingFluentWebDriver input(By by);
    OngoingFluentWebDriver inputs();
    OngoingFluentWebDriver inputs(By by);

    OngoingFluentWebDriver select();
    OngoingFluentWebDriver select(By by);
    OngoingFluentWebDriver selects();
    OngoingFluentWebDriver selects(By by);

    OngoingFluentWebDriver li();
    OngoingFluentWebDriver li(By by);
    OngoingFluentWebDriver lis();
    OngoingFluentWebDriver lis(By by);

    OngoingFluentWebDriver h1();
    OngoingFluentWebDriver h1(By by);
    OngoingFluentWebDriver h1s();
    OngoingFluentWebDriver h1s(By by);

    OngoingFluentWebDriver h2();
    OngoingFluentWebDriver h2(By by);
    OngoingFluentWebDriver h2s();
    OngoingFluentWebDriver h2s(By by);

    OngoingFluentWebDriver h3();
    OngoingFluentWebDriver h3(By by);
    OngoingFluentWebDriver h3s();
    OngoingFluentWebDriver h3s(By by);

    OngoingFluentWebDriver h4();
    OngoingFluentWebDriver h4(By by);
    OngoingFluentWebDriver h4s();
    OngoingFluentWebDriver h4s(By by);

    OngoingFluentWebDriver p();
    OngoingFluentWebDriver p(By by);
    OngoingFluentWebDriver ps();
    OngoingFluentWebDriver ps(By by);

    OngoingFluentWebDriver img();
    OngoingFluentWebDriver img(By by);
    OngoingFluentWebDriver imgs();
    OngoingFluentWebDriver imgs(By by);

    OngoingFluentWebDriver table();
    OngoingFluentWebDriver table(By by);
    OngoingFluentWebDriver tables();
    OngoingFluentWebDriver tables(By by);

    OngoingFluentWebDriver tr();
    OngoingFluentWebDriver tr(By by);
    OngoingFluentWebDriver trs();
    OngoingFluentWebDriver trs(By by);

    OngoingFluentWebDriver td();
    OngoingFluentWebDriver td(By by);
    OngoingFluentWebDriver tds();
    OngoingFluentWebDriver tds(By by);

    OngoingFluentWebDriver th();
    OngoingFluentWebDriver th(By by);
    OngoingFluentWebDriver ths();
    OngoingFluentWebDriver ths(By by);

    OngoingFluentWebDriver ul();
    OngoingFluentWebDriver ul(By by);
    OngoingFluentWebDriver uls();
    OngoingFluentWebDriver uls(By by);

    OngoingFluentWebDriver ol();
    OngoingFluentWebDriver ol(By by);
    OngoingFluentWebDriver ols();
    OngoingFluentWebDriver ols(By by);

    OngoingFluentWebDriver form();
    OngoingFluentWebDriver form(By by);
    OngoingFluentWebDriver forms();
    OngoingFluentWebDriver forms(By by);

    OngoingFluentWebDriver textarea();
    OngoingFluentWebDriver textarea(By by);
    OngoingFluentWebDriver textareas();
    OngoingFluentWebDriver textareas(By by);

    OngoingFluentWebDriver option();
    OngoingFluentWebDriver option(By by);
    OngoingFluentWebDriver options();
    OngoingFluentWebDriver options(By by);


}
