package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildTypeElement extends BasePageElement {
    private SelenideElement name;
    private SelenideElement link;
    private SelenideElement button;

    public BuildTypeElement(SelenideElement element) {
        super(element);
        this.name = find("span[class*='MiddleEllipsis']");
        this.link = find("a");
        this.button = find("button");
    }
}
