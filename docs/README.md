# StockKeep Documentation

Welcome to the StockKeep documentation. This documentation follows the [Diataxis framework](https://diataxis.fr/), organizing content into four distinct types based on user needs.

## Documentation Structure

```
docs/
├── tutorials/          # Learning-oriented ("Teach me...")
├── how-to/             # Goal-oriented ("How do I...?")
├── reference/          # Information-oriented ("What is...?")
├── explanation/        # Understanding-oriented ("Why...?")
└── workflow-examples/  # Practical examples
```

## Quick Start

**New to visual design in StockKeep?** Start here:
1. [Tutorial: Getting Started with Visual Design](tutorials/getting-started-with-design.md)
2. [How-to: Use Compose Previews](how-to/use-compose-previews.md)
3. [Example: Card Redesign](workflow-examples/card-redesign-example.md)

**Need specific information?**
- [Reference: Design Tools](reference/design-tools.md) - Technical details
- [How-to: Customize App Theme](how-to/customize-app-theme.md) - Theme customization
- [Explanation: Visual Design Workflow](explanation/visual-design-workflow.md) - Philosophy

## Documentation Types

### Tutorials (Learning-oriented)

Tutorials take you through a learning experience. They are designed for users who want to learn by doing.

**Question answered:** "Can you teach me to...?"

- [Getting Started with Visual Design](tutorials/getting-started-with-design.md) - Learn Compose Previews from scratch

### How-to Guides (Goal-oriented)

How-to guides help you accomplish a specific task. They assume you know what you want to achieve.

**Question answered:** "How do I...?"

- [Use Compose Previews](how-to/use-compose-previews.md) - Master preview annotations
- [Customize App Theme](how-to/customize-app-theme.md) - Change colors, typography, shapes

### Reference (Information-oriented)

Reference documents describe the machinery. They are factual and complete.

**Question answered:** "What is...?" / "What does X do?"

- [Design Tools Reference](reference/design-tools.md) - Complete technical reference

### Explanation (Understanding-oriented)

Explanations clarify and illuminate. They provide context and background.

**Question answered:** "Why...?" / "Can you explain...?"

- [Visual Design Workflow](explanation/visual-design-workflow.md) - Philosophy and rationale

## Workflow Examples

Practical, end-to-end examples of design workflows:

- [Card Redesign Example](workflow-examples/card-redesign-example.md) - Complete card redesign workflow
- [Complete Theme Example](workflow-examples/complete-theme-example.md) - Building a custom theme from scratch

## Available Commands

When in the devenv shell, the following commands are available:

```bash
# Show preview workflow information
design-preview

# Build debug APK for design testing
design-build
```

## Navigation Guide

| I want to... | Go to... |
|-------------|----------|
| Learn the basics | [Getting Started Tutorial](tutorials/getting-started-with-design.md) |
| Understand @Preview options | [How-to: Use Compose Previews](how-to/use-compose-previews.md) |
| Change app colors | [How-to: Customize App Theme](how-to/customize-app-theme.md) |
| Look up annotation parameters | [Design Tools Reference](reference/design-tools.md) |
| Understand why we use Compose | [Visual Design Workflow](explanation/visual-design-workflow.md) |
| See a complete example | [Workflow Examples](workflow-examples/) |

## Contributing to Documentation

When adding new documentation:

1. **Choose the right type** - Tutorial, How-to, Reference, or Explanation
2. **Keep it focused** - One document = one purpose
3. **Cross-reference** - Link to related documents
4. **Use examples** - Show, don't just tell
5. **Follow existing style** - Match tone and format

## Related Resources

- [Material Design 3 Guidelines](https://m3.material.io/)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Compose Previews Guide](https://developer.android.com/jetpack/compose/tooling/previews)
- [Diataxis Framework](https://diataxis.fr/)

## Questions?

If you can't find what you need:
1. Check the [Design Tools Reference](reference/design-tools.md) for technical details
2. Review the [workflow examples](workflow-examples/) for practical patterns
3. Read the [explanation](explanation/visual-design-workflow.md) for context
