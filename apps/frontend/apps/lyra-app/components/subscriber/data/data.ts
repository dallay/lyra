import { h } from 'vue'
import {
  ArrowDownIcon,
  ArrowRightIcon,
  ArrowUpIcon,
  CheckCircledIcon,
  CircleIcon,
  CircleBackslashIcon
} from '@radix-icons/vue'

export const tags = [
  {
    value: 'bug',
    label: 'Bug',
  },
  {
    value: 'feature',
    label: 'Feature',
  },
  {
    value: 'documentation',
    label: 'Documentation',
  },
]

export const statuses = [
  {
    value: 'ENABLED',
    label: 'Enabled',
    icon: h(CheckCircledIcon),
  },
  {
    value: 'DISABLED',
    label: 'Disabled',
    icon: h(CircleIcon),
  },
  {
    value: 'BLOCKLISTED',
    label: 'Blocklisted',
    icon: h(CircleBackslashIcon),
  },
]

export const priorities = [
  {
    value: 'low',
    label: 'Low',
    icon: h(ArrowDownIcon),
  },
  {
    value: 'medium',
    label: 'Medium',
    icon: h(ArrowRightIcon),
  },
  {
    value: 'high',
    label: 'High',
    icon: h(ArrowUpIcon),
  },
]
