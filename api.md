# Le code dont vous êtes le héros: API

## Un service d'enregistrement
POST /player

### Input

```json
{   
    "name": "Maxime Claveau",
    "mail": "maxime.claveau@zenika.com",
    "language": "js"
}
```

### Output

```json
{   
    "playerId": "iuodhazoiajIOHDiyazd",
    "name": "Maxime Claveau",
    "mail": "maxime.claveau@zenika.com",
    "language": "js"
}
```

## Un service gérant le jeu.
POST /player/{id}/validate

### Input

```json
{   
    "code": "import ..."
}
```

### Output

```json
{   
    "mapImg": "src/blabla",
    "avatarImg": "src/blabla",
    "finished": false,
    "title": "La revanche du méchant",
    "text": "blablabla",
    "mapPosition": {
        "x": 1,
        "y": 2
    },
    "template":"import ...",
    "tests": [
        {
            "title": "Cas simple",
            "input": "input",
            "output": "output"
        },
        {
            "title": "Cas complexe",
            "input": "input",
            "output": "output"
        }
    ],
    "score": 100
}
```

## Un service gérant la compilation
POST /player/{id}/test

### Input & Output

Un service de compilation sera utilisé pour tester que le programme s'exécute correctement et valide les tests.

**Input**
```json
{
    "code": "import blabla ..."
}
```

**Output**
```json
[
    {
        "ok": true,
        "out": "output...",
        "err": "error...",
    }
]
```

## Le format d'un scénario

```json
{
    "id":"azerty",
    "language": "javascript",
    "mapImg": "src/maMap.png",
    "avatarImg": "src/avatar.png",
    "steps": [
        {
            "title": "La revanche du méchant",
            "text": "blablabla",
            "mapPosition": {
                "x": 1,
                "y": 2
            },
            "results": {
                "success": {
                    "finish": false,
                    "step": 2,
                    "score": 30
                 },
                "partialSuccess": {
                    "finish": false,
                    "step": 3,
                    "score": 10,
                    "threshold": 2
                 },
                "failure": {
                    "finish": false,
                    "step": 4,
                    "score": 0
                 }
            },
            "template":"import ...",
            "tests": [
                {
                    "title": "Cas simple",
                    "input": "input",
                    "output": "output"
                },
                {
                    "title": "Cas complexe",
                    "input": "input",
                    "output": "output"
                }
            ],
            "validations": [
                {
                    "title": "Cas simple",
                    "input": "inputBis",
                    "output": "outputBis"
                },
                {
                    "title": "Cas complexe",
                    "input": "inputBis",
                    "output": "outputBis"
                }
            ]
        }
    ]
}
```
