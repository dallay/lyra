import { h } from 'vue'
import {
  ArrowDownIcon,
  ArrowRightIcon,
  ArrowUpIcon,
  CheckCircledIcon,
  CircleIcon,
  CircleBackslashIcon
} from '@radix-icons/vue'
import { useSubscriberStore } from "~/store/subscriber.store";
import type { CountByStatusResponse } from '~/domain/subscriber';

const subscriberStore = useSubscriberStore()
const { subscriberCountByStatus } = subscriberStore

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

 const statusIcons = [
  {
    value: 'ENABLED',
    icon: h(CheckCircledIcon),
  },
  {
    value: 'DISABLED',
    icon: h(CircleIcon),
  },
  {
    value: 'BLOCKLISTED',
    icon: h(CircleBackslashIcon),
  },
]

const loadIcon = (status: string) => {
  const icon = statusIcons.find((icon) => icon.value === status)
  return icon ? icon.icon : h(CircleIcon)
}

const response = await subscriberCountByStatus();
export const statuses = response.data.map((countByStatus: CountByStatusResponse) => ({
  value: countByStatus.status,
  label: countByStatus.status.charAt(0).toUpperCase() + countByStatus.status.slice(1).toLowerCase(),
  count: countByStatus.count,
  icon: loadIcon(countByStatus.status),
}))


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
