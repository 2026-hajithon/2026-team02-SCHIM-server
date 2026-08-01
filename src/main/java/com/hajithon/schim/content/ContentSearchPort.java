package com.hajithon.schim.content;

public interface ContentSearchPort {

    boolean supports(Category category);

        ExternalContentPage search(ContentSearchQuery query);
}
