## Step 1 : Initiation

Vous êtes coincé dans une caverne gardée par de dangereux terroristes.
Tout ce que vous avez à disposition pour construire votre armure
est votre intelligence, votre imagination et votre classe,
vous êtes Tony Stark quand même !

### Exo : Fizzbuzz
```js
// Initialization code DON'T MODIFY
const readline = require('readline')
const lines = []
const input = readline.createInterface({
    input: process.stdin,
    terminal: false,
})
input.on('line', line => lines.push(line))
// End of initialization code

input.on('close', () => {
  // Fizz Buzz

  // Inputs
  const [fizzDivisor, buzzDivisor] = lines[0].split(' ') // first line : 2 integers, the fizz divisor and the buzz divisor
  const n = parseInt(lines[1]) // second line : an integer, the number to count to

  // COMPLETE HERE
  // Count starting from 1 to n included
  // Each number must be written on standard output on a separate line (use console.log)
  // Numbers divisible by fizz divisor must be replaced by 'fizz'
  // Numbers divisible by buzz divisor must be replaced by 'buzz'
  // Numbers divisible by both divisors must be replaced by 'fizzbuzz'

  // Use console.error to debug
})
```

### Résultats
- 100% +10 -> Step 2
- 50% +5 -> Step 3
- < 50% 0 -> Step 3

---

## Step 2 : Première confrontation

Votre armure est faite de pièces rouillées et de boulons de troisième main,
néanmoins elle vous permet de vous échapper de la caverne ! Mais un des terroristes
est mieux équipé que les autres, vous allez devoir utiliser votre meilleure arme
pour vous en sortir !

### Exo : Sort
```js
// Initialization code DON'T MODIFY
const readline = require('readline')
const lines = []
const input = readline.createInterface({
    input: process.stdin,
    terminal: false,
})
input.on('line', line => lines.push(line))
// End of initialization code

input.on('close', () => {
  // Sort

  // Inputs
  const weaponDataSequence = lines[0] // A sequence of digits and letters

  // COMPLETE HERE
  // Calibrate your weapon by sorting in ascending order the sequence
})
```

### Résultats
 - 100% +20 -> Step 4
 - 50% +5 -> Step 3
 - < 50% 0 -> Step 3

---

## Step 3 : L'armure ne fonctionne pas bien

Vous arrivez à sortir de la caverne, mais votre armure n'est pas la plus puissante qui soit.
Elle devient vite instable, et l'un des moteurs au pied s'emballe et vous fait décoller.
Vous vous retrouvez en altitude sans contrôle, vous arrivez juste à orienter votre
chute là où vous pouvez !

### Exo : Max
```js
// Initialization code DON'T MODIFY
const readline = require('readline')
const lines = []
const input = readline.createInterface({
    input: process.stdin,
    terminal: false,
})
input.on('line', line => lines.push(line))
// End of initialization code

input.on('close', () => {
  // Max

  // Inputs
  const engineSequence = lines[0] // A sequence of space separated hexadecimal numbers

  // COMPLETE HERE
  // The right speed for your engine is the greater number of the sequence
})
```

### Résultats
- 100% +10 -> Step 4
- 50% +5 -> Step 4
- < 50% 0 -> Step 4

---

## Step 4 : De retour à la maison

Après avoir été secouru, vous êtes de retour à la Stark Mansion, il est temps
d'utiliser vos moyens de milliardaire pour construire une vraie armure.
L'important, c'est qu'elle soit rutilante !

### Exo : Anagram
```js
// Initialization code DON'T MODIFY
const readline = require('readline')
const lines = []
const input = readline.createInterface({
    input: process.stdin,
    terminal: false,
})
input.on('line', line => lines.push(line))
// End of initialization code

input.on('close', () => {
  // Anagram

  // Inputs
  const sentence1 = lines[0]
  const sentence2 = lines[1]

  // COMPLETE HERE
  // Print ok if sentence1 and sentence2 are anagrams
  // Print ko otherwise
})
```

### Résultats
- 100% +20 -> Step 5
- 50% +5 -> Step 6
- < 50% 0 -> Step 6

---

## Step 5 : Confrontation finale

A peine votre armure rodée, vous tombez nez à nez avec l'armure construite
par votre associé Stane. Il n'est pas ravi de votre retour et veut vous détruire,
vous et votre armure, le combat s'annonce rude !

### Exo : Bit manipulation
```js
// Initialization code DON'T MODIFY
const readline = require('readline')
const lines = []
const input = readline.createInterface({
    input: process.stdin,
    terminal: false,
})
input.on('line', line => lines.push(line))
// End of initialization code

input.on('close', () => {
  // Bit manipulation

  // Inputs
  const binary1 = lines[0]
  const binary2 = lines[1]

  // COMPLETE HERE
  // Print the sum of binary1 and binary2 in binary representation ex: 10001001
})
```

### Résultats
- 100% +10 -> Ending 1
- 50% +5 -> Ending 2
- 0% 0 -> Ending 2

---

## Step 6 : Prototype défaillant

Dans la précipitation de vouloir tout de suite essayer votre armure, vous avez
oublié certains facteurs. Votre armure ne résiste pas à son premier vol d'essai !
Il est temps d'en faire une encore plus puissante et plus rutilante !

### Exo : Duplicate char
```js
// Initialization code DON'T MODIFY
const readline = require('readline')
const lines = []
const input = readline.createInterface({
    input: process.stdin,
    terminal: false,
})
input.on('line', line => lines.push(line))
// End of initialization code

input.on('close', () => {
  // Duplicate char

  // Inputs
  const sentence = lines[0]

  // COMPLETE HERE
  // Print sentence without duplicate chars, keeping only first occurence of each char
})
```

### Résultats
- 100% +10 -> Step 5
- 50% +5 -> Step 5
- < 50% 0 -> Step 5

---

## Ending 1

Vous ne faites qu'une bouchée de Stane et de son armure de pacotille ! Le raffut
a par contre ameuté tous les journalistes. Lorsque l'on vous demande qui est
dans cette armure si rutilante et seyante, vous retirez votre casque pour crier
haut et fort : Je suis Ironman !

---

## Ending 2

Après un combat qui a détruit la moitié de la ville, vous arrivez de justesse à
triompher de Stane ! Les survivants dans les décombres vous acclament, vous pouvez
retourner chez vous le sens du devoir accompli !
