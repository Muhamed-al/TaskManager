# Génération du PDF - Guide JWT Implementation

## Prérequis

Pour générer le PDF à partir du guide Markdown, vous devez installer Python et les dépendances suivantes :

### 1. Installation de Python
- Téléchargez Python depuis [python.org](https://www.python.org/downloads/)
- Assurez-vous que Python est ajouté au PATH

### 2. Installation des Dépendances

Ouvrez un terminal dans le dossier du projet et exécutez :

```bash
pip install markdown2 weasyprint
```

**Note pour Windows :**
Si vous rencontrez des problèmes avec weasyprint sur Windows, vous pouvez utiliser une alternative :

```bash
pip install markdown2 pypandoc
```

## Génération du PDF

### Méthode 1 : Script Python (Recommandé)

1. Assurez-vous que le fichier `JWT_IMPLEMENTATION_GUIDE.md` est complet
2. Exécutez le script Python :

```bash
python generate_pdf.py
```

### Méthode 2 : Pandoc (Alternative)

Si vous préférez utiliser Pandoc :

```bash
pandoc JWT_IMPLEMENTATION_GUIDE.md -o Guide_JWT_Implementation.pdf --pdf-engine=wkhtmltopdf
```

### Méthode 3 : En ligne

Vous pouvez également utiliser des outils en ligne :

1. **Markdown to PDF** : [md-to-pdf.com](https://md-to-pdf.com)
2. **Dillinger** : [dillinger.io](https://dillinger.io)
3. **StackEdit** : [stackedit.io](https://stackedit.io)

## Structure des Fichiers

```
TaskManager/
├── JWT_IMPLEMENTATION_GUIDE.md    # Guide complet en Markdown
├── generate_pdf.py                 # Script de génération PDF
├── README_PDF_GENERATION.md        # Ce fichier
└── Guide_JWT_Implementation.pdf    # PDF généré (après exécution)
```

## Contenu du Guide

Le guide `JWT_IMPLEMENTATION_GUIDE.md` contient :

### 📋 **Sections Principales :**
1. **Introduction** - Concepts JWT et architecture
2. **Configuration des Dépendances** - Maven et propriétés
3. **Service JWT** - Implémentation complète
4. **Spring Security** - Configuration et filtres
5. **Entités et DTOs** - User, Roles, DTOs
6. **Services d'Authentification** - Login et Register
7. **Contrôleurs** - Endpoints REST
8. **Gestion d'Exceptions** - Error handling
9. **Tests** - Exemples avec cURL
10. **Conclusion** - Résumé et prochaines étapes

### 🔧 **Code Snippets Inclus :**
- Configuration Maven complète
- Service JWT avec toutes les méthodes
- Configuration Spring Security
- Entité User avec UserDetails
- DTOs pour l'authentification
- Service d'authentification
- Contrôleurs REST
- Gestionnaire d'exceptions
- Exemples de tests

### 📊 **Diagrammes et Schémas :**
- Architecture JWT
- Flux d'authentification
- Structure du projet
- Endpoints disponibles

## Résolution de Problèmes

### Erreur : ModuleNotFoundError
```bash
pip install markdown2 weasyprint
```

### Erreur : weasyprint sur Windows
Utilisez une alternative :
```bash
pip install markdown2 pypandoc
```

### Erreur : Fichier Markdown incomplet
Vérifiez que le fichier `JWT_IMPLEMENTATION_GUIDE.md` contient tout le contenu.

## Utilisation du PDF

Le PDF généré peut être :
- **Partagé avec les étudiants** pour l'apprentissage
- **Imprimé** pour référence papier
- **Utilisé comme documentation** du projet
- **Intégré dans un cours** sur l'authentification JWT

## Personnalisation

Pour personnaliser le style du PDF, modifiez le CSS dans `generate_pdf.py` :

```python
# Exemple de personnalisation
body {
    font-family: 'Arial', sans-serif;
    font-size: 12pt;
    line-height: 1.5;
}
```

## Support

Si vous rencontrez des problèmes :
1. Vérifiez que Python est installé : `python --version`
2. Vérifiez les dépendances : `pip list`
3. Consultez la documentation de weasyprint : [weasyprint.org](https://weasyprint.org) 