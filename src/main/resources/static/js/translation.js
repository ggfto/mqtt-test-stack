function translatePhrase(language, key, text) {
    return translations[language][key] || text;
}

function translate() {
    let language = getLanguage();
    for(let item in translations[language]) {
        translateItem(language, item);
    }
}

function translateItem(language, item) {
    $("body").find(`[data-key="${item}"]`).text(translatePhrase(language, item, $("body").find(`[data-key="${item}"]`).text()));
}

function getLanguage() {
    return window.navigator.userLanguage || window.navigator.language;
}

const translations = {
    "pt-BR" : {
        "subtitle": "Testes MQTT",
        "topic": "Tópico MQTT",
        "message": "Mensagem",
        "retained": "Retido",
        "qos": "QOS",
        "publish": "Publicar",
        "publishing": "Publicando",
        "subscribe": "Inscrever",
        "stop": "Parar",
        "publisherLabel": "Publicador",
        "subscriberLabel": "Subscritor",
        "response": "Resposta",
        "repeat": "Repetir a cada",
        "seconds": "milisegundo(s)."
    }
}
