# Example: Redesigning the Inventory Card

This example demonstrates a complete workflow for redesigning the inventory item card in StockKeep using Compose Previews.

## Scenario

You want to redesign the inventory item card to:
- Use a new color scheme (blue primary)
- Show more information (category icon)
- Improve visual hierarchy
- Work well in dark mode

## Step 1: Set Up Previews

First, create comprehensive previews of the current design:

```kotlin
// In InventoryScreen.kt

@Preview(
    name = "Current Card - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Current Card - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun CurrentCardDesignPreview() {
    StockKeepTheme {
        val sampleItem = InventoryItem(
            id = 1,
            name = "Wireless Mouse",
            barcode = "123456789",
            quantity = 5,
            price = 29.99
        )
        InventoryItemCard(item = sampleItem)
    }
}

@Preview(name = "Card - Long Name")
@Composable
fun LongNameCardPreview() {
    StockKeepTheme {
        val sampleItem = InventoryItem(
            id = 2,
            name = "Premium Wireless Bluetooth Noise-Cancelling Headphones with Extra Long Name",
            barcode = "987654321",
            quantity = 0,
            price = 199.99
        )
        InventoryItemCard(item = sampleItem)
    }
}

@Preview(name = "Card - Large Quantity")
@Composable
fun LargeQuantityCardPreview() {
    StockKeepTheme {
        val sampleItem = InventoryItem(
            id = 3,
            name = "Paper Clips",
            barcode = "111222333",
            quantity = 9999,
            price = 0.99
        )
        InventoryItemCard(item = sampleItem)
    }
}
```

## Step 2: Document Current State

Save screenshots of the current previews to `docs/design-screenshots/before/`.

## Step 3: Iterate on Colors

Update `Color.kt`:

```kotlin
// New color palette
val Blue40 = Color(0xFF1976D2)
val Blue80 = Color(0xFF90CAF9)
val Blue90 = Color(0xFFE3F2FD)
val Teal40 = Color(0xFF00796B)
val Teal80 = Color(0xFF80CBC4)

// Keep existing for reference (commented)
// val Purple40 = Color(0xFF6650a4)
// val Purple80 = Color(0xFFD0BCFF)
```

Update `Theme.kt`:

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue40,
    secondary = Teal40,
    onSecondary = Color.White,
    // ... rest of scheme
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Color(0xFF0D3A5C),
    primaryContainer = Color(0xFF0D3A5C),
    onPrimaryContainer = Blue80,
    secondary = Teal80,
    onSecondary = Color(0xFF003D34),
    // ... rest of scheme
)
```

Watch the previews update instantly with new colors.

## Step 4: Redesign the Card Component

Update `InventoryItemCard` with new design:

```kotlin
@Composable
fun InventoryItemCard(
    item: InventoryItem,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header row with icon and name
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Inventory,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Info row with quantity and price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Quantity badge
                Surface(
                    color = if (item.quantity > 10) 
                        MaterialTheme.colorScheme.primaryContainer 
                    else 
                        MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "${item.quantity} in stock",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = if (item.quantity > 10)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                
                // Price
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Barcode
            Text(
                text = "SKU: ${item.barcode}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
```

## Step 5: Test All States

Add comprehensive previews for the new design:

```kotlin
@Preview(name = "New Card - Normal Stock")
@Composable
fun NewCardNormalStockPreview() {
    StockKeepTheme {
        InventoryItemCard(
            item = InventoryItem(
                id = 1,
                name = "Wireless Mouse",
                barcode = "123456789",
                quantity = 50,
                price = 29.99
            )
        )
    }
}

@Preview(name = "New Card - Low Stock")
@Composable
fun NewCardLowStockPreview() {
    StockKeepTheme {
        InventoryItemCard(
            item = InventoryItem(
                id = 2,
                name = "USB Cable",
                barcode = "987654321",
                quantity = 5,
                price = 9.99
            )
        )
    }
}

@Preview(name = "New Card - Zero Stock")
@Composable
fun NewCardZeroStockPreview() {
    StockKeepTheme {
        InventoryItemCard(
            item = InventoryItem(
                id = 3,
                name = "Old Product",
                barcode = "111222333",
                quantity = 0,
                price = 49.99
            )
        )
    }
}

@Preview(name = "New Card - Long Name")
@Composable
fun NewCardLongNamePreview() {
    StockKeepTheme {
        InventoryItemCard(
            item = InventoryItem(
                id = 4,
                name = "Premium Wireless Bluetooth Noise-Cancelling Headphones with Extra Long Product Name",
                barcode = "444555666",
                quantity = 12,
                price = 199.99
            )
        )
    }
}

@Preview(name = "New Card - Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewCardDarkThemePreview() {
    StockKeepTheme(darkTheme = true) {
        InventoryItemCard(
            item = InventoryItem(
                id = 5,
                name = "Wireless Mouse",
                barcode = "123456789",
                quantity = 50,
                price = 29.99
            )
        )
    }
}
```

## Step 6: Compare Before and After

Create a comparison preview:

```kotlin
@Preview(name = "Design Comparison", widthDp = 800)
@Composable
fun CardComparisonPreview() {
    StockKeepTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Note: This requires keeping the old design function
            Column(modifier = Modifier.weight(1f)) {
                Text("BEFORE", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))
                OldInventoryItemCard(
                    item = InventoryItem(
                        id = 1,
                        name = "Wireless Mouse",
                        barcode = "123456789",
                        quantity = 50,
                        price = 29.99
                    )
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text("AFTER", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))
                InventoryItemCard(
                    item = InventoryItem(
                        id = 1,
                        name = "Wireless Mouse",
                        barcode = "123456789",
                        quantity = 50,
                        price = 29.99
                    )
                )
            }
        }
    }
}
```

## Step 7: Document Changes

1. Save all preview screenshots to `docs/design-screenshots/after/`
2. Create a summary document:

```markdown
## Card Redesign Summary

### Changes Made
- Updated color scheme to blue/teal
- Added category icon to card header
- Improved quantity badge with color coding
- Enhanced visual hierarchy with proper spacing
- Added proper dark mode support

### Files Modified
- Color.kt - New color palette
- Theme.kt - Updated color schemes
- InventoryScreen.kt - New card design

### Preview Screenshots
- See docs/design-screenshots/after/
```

## Step 8: Test on Device

Build and test on actual device:

```bash
design-build
# Install APK and verify
```

## Time Comparison

### Traditional Workflow (Figma → Code)
- Design in Figma: 2 hours
- Export assets: 30 minutes
- Developer handoff meeting: 1 hour
- Implementation: 3 hours
- Review and iteration: 2 hours
- **Total: 8.5 hours**

### Compose Preview Workflow
- Set up previews: 15 minutes
- Iterate on colors: 30 minutes
- Redesign component: 1 hour
- Test all states: 30 minutes
- Build and verify: 15 minutes
- **Total: 2.5 hours**

**Time saved: 6 hours (71% reduction)**

## Key Takeaways

1. **Previews guide the design** - You designed by seeing, not imagining
2. **States matter** - Testing edge cases (long names, low stock) caught issues early
3. **Theme consistency** - Colors automatically work in both light and dark
4. **No export/import** - Design IS the implementation
5. **Version controlled** - Every design iteration is a Git commit

## Next Steps

- Apply similar redesign to other components
- Create design tokens for spacing and elevations
- Document the new design system
- Share screenshots with stakeholders
