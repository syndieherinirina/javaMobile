# Documentation Android

## Informations personnelles

- **Email :** tohainatsaroana@gmail.com
- **Contact :** 0349520546
- **Portfolio :** tohainatsaroana.vercel.app

> A completer tout en haut de la page : nom, prenoms et numero d'inscription.

## A faire

- Creer un portfolio.
- Creer un compte LinkedIn.
- Envoyer seulement les fichiers `.java` et `.xml` concernes.

## Commandes utiles

Verifier la version de `adb` :

```bash
adb --version
```

Compiler le projet Android :

```bash
./gradlew assembleDebug
```

Installer l'application :

```bash
adb install -r app-debug.apk
adb install -g app-debug.apk
```

## Bases d'une application Android

Une application Android contient deux parties principales :

- **Partie visuelle :** XML
- **Partie logique :** Java

### Composants principaux

- **Activity :** base pour creer un ecran.
- **TextView :** composant utilise pour afficher et gerer du texte.
- **View :** composant graphique affiche a l'ecran.
- **Layout :** conteneur qui organise les vues dans l'ecran.

Une activite represente un ecran dans une application Android. Au demarrage de l'activite, on associe un layout avec la methode `setContentView`.

## Attributs XML courants

### Texte

- `android:textSize` : taille du texte.
- `android:textColor` : couleur du texte.
- `android:textStyle` : style du texte, par exemple gras ou italique.
- `android:gravity` : position du contenu dans la vue (`center`, `right`, `left`, etc.).

### Espacement

- `android:padding` : marge interieure, mesuree en `dp` ou `sp`.
- `android:layout_margin` : marge exterieure, mesuree en `dp` ou `sp`.

Exemples :

```xml
android:padding="24dp"
android:layout_margin="16dp"
```

## Exercices

### 1. Formulaire de connexion

**Objectif :** creer un formulaire avec :

- un champ email ;
- un champ mot de passe ;
- un bouton de connexion.

### 2. Validation des champs

**Objectif :** verifier que les champs ne sont pas vides avant d'afficher le resultat.

**Consignes :**

- Si l'email est vide, afficher : `Email requis`.
- Si le mot de passe est vide, afficher : `Mot de passe requis`.
- Si les deux champs sont remplis, transformer le texte `Connexion` en `Connexion loading`.

### 3. Validation de l'email

**Objectif :** verifier que l'email contient bien un `@` et un point.

**Consignes :**

- Verifier qu'il y a un `@`.
- Verifier qu'il y a un `.` apres le `@`.
- Afficher un message personnalise si l'email ne respecte pas les conditions.

### 4. Compteur de tentatives

**Objectif :** limiter le nombre de tentatives de connexion.

**Consignes :**

- Declarer une variable `int tentative = 0` au debut.
- A chaque tentative echouee, faire `tentative++`.
- Apres trois tentatives echouees, desactiver le bouton en le grisant ou afficher une notification `Toast`.

### 5. Affichage dynamique

**Objectif :** afficher la saisie en temps reel dans un `TextView`.

**Consigne :** ajouter un `TextView` au-dessous du bouton de connexion, puis mettre a jour son contenu a chaque frappe dans le champ email.

### 6. Bouton effacer

**Objectif :** ajouter un bouton qui vide tous les champs.

**Consignes :**

- Ajouter un deuxieme bouton `Effacer`.
- Au clic, vider les champs.
- Apres l'effacement, remettre le curseur dans le champ email.

### 7. Afficher ou masquer le mot de passe

**Objectif :** ajouter une icone cliquable pour afficher ou cacher le mot de passe.

**Consignes :**

- Au premier clic, rendre le mot de passe visible.
- Au second clic, masquer de nouveau le mot de passe.

### 8. Changement de couleur

**Objectif :** changer la couleur du bouton selon la saisie.

**Consigne :** si l'email et le mot de passe sont remplis, le bouton devient vert. Sinon, il reste gris.

### 9. Changement d'activite

**Objectif :** ouvrir une nouvelle fenetre lorsque la connexion reussit.

**Consignes :**

- Creer une deuxieme activite : `AccueilActivity`.
- Apres une connexion reussie, naviguer vers cette nouvelle activite.
- Ajouter un bouton retour pour revenir a l'ecran de connexion.

### 10. Memorisation des identifiants

**Objectif :** sauvegarder l'email afin de ne pas le retaper a chaque lancement.

**Consignes :**

- Ajouter une case a cocher : `Se souvenir de moi`.
- Si elle est cochee, sauvegarder l'email dans `SharedPreferences`.
- Au prochain lancement, afficher automatiquement l'email dans le champ.

## Personnalisation de l'interface

Modifier l'interface en ajoutant :

- une couleur de fond ;
- une couleur personnalisee pour les textes ;
- un bouton supplementaire.

## Layouts et positionnement

### Valeurs de taille

- `match_parent` : le composant prend autant que possible la taille du composant parent.
- `wrap_content` : le composant prend seulement la taille necessaire pour afficher son contenu.
- `fill_parent` : ancien nom de `match_parent`.

### Marges et padding

Exemples d'attributs de marge :

```xml
android:layout_marginBottom="16dp"
android:layout_marginTop="16dp"
android:layout_marginLeft="16dp"
android:layout_marginRight="16dp"
android:layout_marginHorizontal="16dp"
android:layout_marginVertical="16dp"
android:layout_marginStart="16dp"
android:layout_marginEnd="16dp"
```

Exemples d'attributs de padding :

```xml
android:paddingBottom="16dp"
android:paddingTop="16dp"
android:paddingLeft="16dp"
android:paddingRight="16dp"
android:paddingHorizontal="16dp"
android:paddingVertical="16dp"
android:paddingStart="16dp"
android:paddingEnd="16dp"
```

### Gravity

Les attributs `android:layout_gravity` et `android:gravity` permettent de definir comment le contenu d'une vue doit etre positionne : en haut, en bas, a gauche, a droite ou au centre.

### Layout weight

L'attribut `android:layout_weight` permet de definir un poids pour chaque vue dans un layout.

- Dans un layout vertical, la hauteur de chaque vue est proportionnelle a son poids.
- Dans un layout horizontal, la largeur de chaque vue est proportionnelle a son poids.
- Si la valeur est inferieure a `1`, elle peut etre interpretee comme un pourcentage. Exemple : `0.5` correspond a `50%`.

## ConstraintLayout

Le principe de `ConstraintLayout` est de positionner les vues les unes par rapport aux autres.

Syntaxe generale :

```xml
app:layout_constraintX_toYOf="@id/vue"
```

Exemples :

- `app:layout_constraintTop_toTopOf` : aligner le haut avec le haut d'une autre vue.
- `app:layout_constraintTop_toBottomOf` : placer une vue sous une autre vue.
- `app:layout_constraintEnd_toEndOf` : aligner la fin avec la fin d'une autre vue.
- `app:layout_constraintStart_toStartOf` : aligner le debut avec le debut d'une autre vue.
- `app:layout_constraintStart_toEndOf` : placer une vue apres une autre vue.

Exemple :

```xml
<TextView
    android:text="Bonjour"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toBottomOf="@id/btnRetour" />

<Button
    android:id="@+id/btnRetour"
    android:text="Cliquer ici"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

## Afficher une image

La vue `ImageView` permet d'inserer une image.

Android permet de fournir plusieurs versions d'une meme ressource selon la resolution de l'appareil. Le format `WebP` est souvent utilise pour reduire la taille des images.

### Densites d'ecran

- `ldpi` : low dots per inch, environ `120 dpi`, facteur `0.75`.
- `mdpi` : medium dots per inch, environ `160 dpi`, facteur `1`.
- `hdpi` : high dots per inch, environ `240 dpi`, facteur `1.5`.
- `xhdpi` : extra high dots per inch, environ `320 dpi`, facteur `2`.
- `xxhdpi` : environ `480 dpi`, facteur `3`.
- `xxxhdpi` : environ `640 dpi`, facteur `4`.

## Cycle de vie d'une activite

- `onStart` : l'ecran devient visible.
- `onResume` : l'utilisateur peut interagir avec l'ecran.
- `onPause` : l'ecran n'est plus au premier plan.
- `onStop` : l'ecran n'est plus visible.
- `onDestroy` : l'ecran est detruit.

## Gestion de l'etat d'une activite

Un objet `Activity` peut etre detruit et recree plusieurs fois. L'objectif est que l'utilisateur ait toujours l'impression de retrouver le meme ecran, meme apres une recreation de l'activite.

## Derniere consigne

Creer un bouton comme dans la page d'accueil du projet React Native.
Essayer d'enlever le SaveInstance dans accueilActivity.java.
