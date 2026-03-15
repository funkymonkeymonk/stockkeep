# Visual Design Workflow

This document explains the philosophy and rationale behind the visual design workflow for StockKeep.

## Why Compose Previews?

Traditional mobile app design often separates design and development:
1. Designers create mockups in Figma, Sketch, or Adobe XD
2. Developers implement the designs in code
3. Discrepancies emerge between design and implementation
4. Back-and-forth iteration consumes time

**Compose Previews eliminate this gap.** They allow visual design to happen directly in code, with instant feedback and no translation layer.

## The Code-As-Design Approach

### Single Source of Truth

When you use Compose Previews, your design IS your implementation:

- **Colors** defined in `Color.kt` are the actual app colors
- **Typography** in `Type.kt` is the actual text rendering
- **Layouts** in screen files are the actual UI structure
- **Components** previewed are the actual production components

This eliminates the "design handoff" problem where designs differ from implementation.

### Immediate Feedback Loop

Traditional workflow:
```
Change color in design tool → Export → Review → Implement → Build → Test → (find issues) → Repeat
```

Compose Preview workflow:
```
Change color in code → See instantly in Preview → Adjust → Done
```

This compression of the feedback loop enables rapid iteration.

## Benefits for StockKeep

### 1. Inventory App Specifics

Inventory apps have repetitive UI patterns:
- List items with consistent cards
- Form inputs with similar styling
- Scan interfaces with overlays
- Data-dense displays

Compose Previews let you:
- Perfect one card design, then replicate
- Test with realistic inventory data
- Verify readability with long product names
- Ensure scanning UI works in both themes

### 2. Android-First Design

StockKeep is an Android-native app. Compose is the modern Android UI toolkit. Using Compose Previews ensures:
- Designs respect Android conventions
- Material 3 components used correctly
- Accessibility features (TalkBack, font scaling) work
- Dark mode implemented properly

### 3. No Additional Tools Required

Unlike design tools that require:
- Separate licenses (Figma, Adobe)
- Installation and updates
- Export/import workflows
- Design system maintenance

Compose Previews are:
- Built into Android Studio
- Always in sync with code
- Version controlled with Git
- Free and open source

## Trade-offs and Limitations

### What You Gain

- **Speed**: Instant visual feedback
- **Accuracy**: Design equals implementation
- **Collaboration**: Designers can work directly in the codebase
- **Versioning**: Design changes tracked in Git
- **Testing**: Preview multiple states easily

### What You Lose

- **Vector editing**: No pen tool or bezier curves
- **Asset creation**: Still need tools for complex icons/illustrations
- **Multi-platform previews**: Android-only (though Compose Multiplatform exists)
- **Stakeholder review**: Non-technical stakeholders may prefer design tool links

### When to Use External Tools

Even with Compose Previews, you might still want:

1. **Penpot or Figma**: For initial concept exploration, stakeholder presentations, or complex illustrations
2. **Vector editors**: For custom icons that require complex shapes
3. **Image editors**: For photo manipulation or raster assets

For StockKeep specifically:
- Use Compose Previews for 90% of design work (screens, components, themes)
- Use external tools only for the app icon and any complex illustrations

## The Iterative Design Process

### Phase 1: Theme Foundation (Day 1)

Establish the visual foundation:
1. Define color palette in `Color.kt`
2. Set typography scale in `Type.kt`
3. Create a preview showing all theme elements
4. Test in both light and dark modes

### Phase 2: Component Library (Day 2-3)

Build reusable components with previews:
1. Inventory item card
2. Scan button and overlay
3. Form inputs (text fields, dropdowns)
4. Empty states and loading indicators

Each component gets:
- Multiple state previews (normal, pressed, disabled)
- Theme variant previews (light, dark)
- Edge case previews (long text, zero quantity)

### Phase 3: Screen Assembly (Day 4-5)

Compose screens from components:
1. Inventory list screen
2. Scan screen with camera overlay
3. Add/Edit item dialogs
4. Settings/preferences screen

Each screen gets:
- Full-screen previews
- Interactive mode testing
- Multiple device size previews

### Phase 4: Polish (Ongoing)

Continuous refinement:
1. Animation previews
2. Accessibility testing (font scaling, contrast)
3. Edge case handling
4. Performance optimization

## Collaboration with Compose Previews

### Designer + Developer Workflow

**Traditional:**
- Designer works in Figma
- Hands off designs to developer
- Developer implements
- Designer reviews build
- Iterate

**With Compose Previews:**
- Designer checks out code
- Adds `@Preview` annotations
- Makes visual changes directly
- Submits PR with screenshots
- Developer reviews code
- Merge

### Sharing Design Progress

Export previews for stakeholders:
1. Right-click preview → Save Image
2. Commit images to `docs/design-screenshots/`
3. Reference in PR descriptions
4. Include in documentation

This creates a visual changelog of design evolution.

## Best Practices for This Workflow

### 1. Preview-Driven Development

Always start with a preview:
```kotlin
@Preview
@Composable
fun NewComponentPreview() {
    // Build this first
}
```

Then implement the actual component.

### 2. State Coverage

Preview all states:
- Loading
- Empty
- Error
- Success with data
- Interaction states

### 3. Theme Testing

Always check both themes:
```kotlin
@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
```

### 4. Realistic Data

Use sample data that resembles production:
```kotlin
val sampleItem = InventoryItem(
    name = "Wireless Bluetooth Headphones",
    quantity = 42,
    price = 79.99
)
```

### 5. Document Decisions

When you make design choices, document why:
```kotlin
// Using secondary color for delete actions
// to indicate destructive operations
IconButton(
    onClick = { deleteItem() },
    colors = IconButtonDefaults.iconButtonColors(
        contentColor = MaterialTheme.colorScheme.error
    )
)
```

## Comparison with Alternatives

### vs. Figma/Sketch

| Aspect | Compose Previews | Figma |
|--------|-----------------|-------|
| **Implementation** | Code is design | Separate handoff |
| **Iteration speed** | Instant | Fast |
| **Real data** | Native | Simulated |
| **Developer learning** | Requires code | Visual only |
| **Asset export** | Not needed | Required |
| **Cost** | Free | Freemium/Paid |

### vs. XML Layouts

| Aspect | Compose Previews | XML |
|--------|-----------------|-----|
| **Preview fidelity** | High | Limited |
| **Interactivity** | Full (Android Studio EE+) | None |
| **Code coupling** | Integrated | Separate |
| **Learning curve** | Kotlin required | XML learning |
| **Modern features** | Full Material 3 | Partial |

## Future Possibilities

### Compose Multiplatform

If StockKeep expands to other platforms:
- Share previews across Android, Desktop, iOS
- Single design system for all platforms
- Consistent user experience

### Design System Export

Tools exist to:
- Export Compose themes to Figma
- Generate Compose from design tokens
- Sync design systems bidirectionally

### AI-Assisted Design

Emerging capabilities:
- Generate previews from text descriptions
- Auto-suggest color palettes
- Convert screenshots to Compose code

## Conclusion

The Compose Preview workflow aligns with modern Android development best practices. It leverages the fact that StockKeep is a **Compose-native app** to make the design process:

- Faster (instant feedback)
- More accurate (design equals code)
- Better integrated (no tool switching)
- More maintainable (version controlled)

For a data-driven inventory app with consistent UI patterns, this approach is ideal. It keeps the focus on user experience while maintaining the efficiency of working directly in the development environment.

## Related Documentation

- [Getting Started with Visual Design](../tutorials/getting-started-with-design.md) - Tutorial
- [How-to: Use Compose Previews](../how-to/use-compose-previews.md) - Practical guide
- [How-to: Customize App Theme](../how-to/customize-app-theme.md) - Theme customization
- [Design Tools Reference](../reference/design-tools.md) - Technical reference
