package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class ProjectElement extends BasePageElement {
    private final SelenideElement name;
    private final SelenideElement link;
    private final SelenideElement button;

    public ProjectElement(SelenideElement element) {
        super(element);
        this.name = find("span[class*='MiddleEllipsis']");
        this.link = find("a");
        this.button = find("button");
    }
}
