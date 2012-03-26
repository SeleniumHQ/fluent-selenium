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

    FluentWebElement span();
    FluentWebElement span(By by);
    FluentWebElements spans();
    FluentWebElements spans(By by);

    FluentWebElement div();
    FluentWebElement div(By by);
    FluentWebElements divs();
    FluentWebElements divs(By by);

    FluentWebElement button();
    FluentWebElement button(By by);
    FluentWebElements buttons();
    FluentWebElements buttons(By by);

    FluentWebElement link();
    FluentWebElement link(By by);
    FluentWebElements links();
    FluentWebElements links(By by);

    FluentWebElement input();
    FluentWebElement input(By by);
    FluentWebElements inputs();
    FluentWebElements inputs(By by);

    FluentSelect select();
    FluentSelect select(By by);
    FluentSelects selects();
    FluentSelects selects(By by);

    FluentWebElement li();
    FluentWebElement li(By by);
    FluentWebElements lis();
    FluentWebElements lis(By by);

    FluentWebElement h1();
    FluentWebElement h1(By by);
    FluentWebElements h1s();
    FluentWebElements h1s(By by);

    FluentWebElement h2();
    FluentWebElement h2(By by);
    FluentWebElements h2s();
    FluentWebElements h2s(By by);

    FluentWebElement h3();
    FluentWebElement h3(By by);
    FluentWebElements h3s();
    FluentWebElements h3s(By by);

    FluentWebElement h4();
    FluentWebElement h4(By by);
    FluentWebElements h4s();
    FluentWebElements h4s(By by);

    FluentWebElement p();
    FluentWebElement p(By by);
    FluentWebElements ps();
    FluentWebElements ps(By by);

    FluentWebElement img();
    FluentWebElement img(By by);
    FluentWebElements imgs();
    FluentWebElements imgs(By by);

    FluentWebElement table();
    FluentWebElement table(By by);
    FluentWebElements tables();
    FluentWebElements tables(By by);

    FluentWebElement fieldset();
    FluentWebElements fieldsets();
    FluentWebElement fieldset(By by);
    FluentWebElements fieldsets(By by);

    FluentWebElement legend();
    FluentWebElements legends();
    FluentWebElement legend(By by);
    FluentWebElements legends(By by);

    FluentWebElement tr();
    FluentWebElement tr(By by);
    FluentWebElements trs();
    FluentWebElements trs(By by);

    FluentWebElement td();
    FluentWebElement td(By by);
    FluentWebElements tds();
    FluentWebElements tds(By by);

    FluentWebElement th();
    FluentWebElement th(By by);
    FluentWebElements ths();
    FluentWebElements ths(By by);

    FluentWebElement ul();
    FluentWebElement ul(By by);
    FluentWebElements uls();
    FluentWebElements uls(By by);

    FluentWebElement ol();
    FluentWebElement ol(By by);
    FluentWebElements ols();
    FluentWebElements ols(By by);

    FluentWebElement form();
    FluentWebElement form(By by);
    FluentWebElements forms();
    FluentWebElements forms(By by);

    FluentWebElement textarea();
    FluentWebElement textarea(By by);
    FluentWebElements textareas();
    FluentWebElements textareas(By by);

    FluentWebElement option();
    FluentWebElement option(By by);
    FluentWebElements options();
    FluentWebElements options(By by);

    FluentWebElement map();
    FluentWebElements maps();
    FluentWebElement map(By by);
    FluentWebElements maps(By by);

    FluentWebDriver within(Period p);
    FluentWebDriver recordTo(FluentRecording fluentRecording);

    TestableString url();
    TestableString title();


}
