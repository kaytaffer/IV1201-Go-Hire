import React from "react";
import {useTranslation} from "react-i18next";
import {LanguageSelectorView} from "../view/languageSelectorView";
import {languageList} from "./i18n/i18nConfig";

/**
 * Responsible for the logic of the language selector.
 * @param props props.
 * @returns {JSX.Element} the rendered language selector.
 */
export function LanguageSelector(props) {
    const { i18n } = useTranslation();

    function onLanguageChange(language) {
        i18n.changeLanguage(language)
    }

    return <LanguageSelectorView languageList={languageList}
                                 onLanguageChange={onLanguageChange}/>
}