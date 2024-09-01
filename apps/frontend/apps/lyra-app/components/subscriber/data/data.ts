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
    label: 'ENABLED',
    icon: h(CheckCircledIcon),
  },
  {
    value: 'DISABLED',
    label: 'DISABLED',
    icon: h(CircleIcon),
  },
  {
    value: 'BLOCKLISTED',
    label: 'BLOCKLISTED',
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
