import React from "react";

/**
 * Responsible for rendering the language selector dropdown.
 * @param props props.
 * @param {{name: *, key: *}[]} props.languageList - list of available language for internationalization
 * @param {Function} props.onLanguageChange - called when the user chose a new language.
 * @param {Function} props.t - translation function for internationalization.
 * @returns {JSX.Element} - the rendered language selector dropdown.
 */
export function LanguageSelectorView(props) {

    function languageSelected() {
        props.onLanguageChange(document.getElementById("language-picker").value)
    }

    return (
        <select id="language-picker" name="language-picker" onChange={languageSelected}>
            {props.languageList.map(language =>
                <option value={language.key} key={language.key}>
                    {language.name}
                </option>)}
        </select>
    )
}