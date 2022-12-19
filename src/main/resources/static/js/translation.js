const translations = {
    "pt-BR" : {
        "subtitle": "Testes MQTT",
        "topic": "Tópico MQTT",
        "message": "Mensagem",
        "retained": "Retido",
        "qos": "QOS",
        "publish": "Publicar",
        "subscribe": "Inscrever",
        "stop": "Parar",
        "publisherLabel": "Publicador",
        "subscriberLabel": "Subscritor",
        "response": "Resposta",
        "repeat": "Repetir a cada",
        "seconds": "milisegundo(s)."
    }
}

function translatePhrase(language, key, text) {
    return translations[language][key] || text;
}

function translate() {
    let language = window.navigator.userLanguage || window.navigator.language;
    for(let item in translations[language]) {
        $("body").find(`[data-key="${item}"]`).text(translatePhrase(language, item, $("body").find(`[data-key="${item}"]`).text()));
    }
}