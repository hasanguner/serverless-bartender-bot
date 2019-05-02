package net.hasanguner.lex

class InvalidIntentException(intentName: String) : RuntimeException("Intent name ($intentName) is not recognized")