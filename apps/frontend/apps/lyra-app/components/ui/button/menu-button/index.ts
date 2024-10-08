import { type VariantProps, cva } from 'class-variance-authority'

export { default as MenuButton } from './MenuButton.vue'

export const menuButtonVariants = cva(
  'relative z-0 inline-flex shadow-sm rounded-md w-full',
  {
    variants: {
      variant: {
        default: 'bg-primary text-primary-foreground shadow hover:bg-primary/90',
        outline:
          'border border-input bg-background shadow-sm hover:bg-accent hover:text-accent-foreground',
      },
      size: {
        default: 'rounded-md text-sm',
        xs: 'rounded',
        sm: 'rounded-md text-xs',
        lg: 'rounded-md',
        icon: 'size-4',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  },
)

export type MenuButtonVariants = VariantProps<typeof menuButtonVariants>
