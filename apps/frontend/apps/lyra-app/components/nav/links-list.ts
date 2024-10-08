import type { LinkProp } from "./LinkProp";

const generateId = (title: string): string => {
  return title.toLowerCase().replace(/\s+/g, '-');
};

const navigationLinks: LinkProp[] = [
  {
    id: generateId('Dashboard'),
    title: 'Dashboard',
    to: '/',
    icon: 'lucide:layout-dashboard',
    variant: 'default',
  },
  {
    id: generateId('Start Writing'),
    title: 'Start Writing',
    to: '/',
    icon: 'lucide:layout-dashboard',
    variant: 'default',
  },
  {
    id: generateId('Audience'),
    title: 'Audience',
    icon: 'ph:users',
    variant: 'ghost',
    sub: [
      {
        id: generateId('Subscribers'),
        title: 'Subscribers',
        icon: 'lucide:user-2',
        to: '/subscribers',
        variant: 'ghost',
      },
      {
        id: generateId('Subscribe Forms'),
        title: 'Subscribe Forms',
        to: '/forms/subscribe',
        icon: 'material-symbols-light:dynamic-form-outline-rounded',
        variant: 'ghost',
      },
    ],
  },
  {
    id: generateId('Sent'),
    title: 'Sent',
    to: '/subscribers',
    icon: 'lucide:send',
    variant: 'ghost',
  },
  {
    id: generateId('Junk'),
    title: 'Junk',
    label: '23',
    icon: 'lucide:archive',
    variant: 'ghost',
  },
  {
    id: generateId('Trash'),
    title: 'Trash',
    label: '',
    icon: 'lucide:trash',
    variant: 'ghost',
  },
  {
    id: generateId('Archive'),
    title: 'Archive',
    label: '',
    icon: 'lucide:archive',
    variant: 'ghost',
  },
  {
    id: generateId('Without Icon'),
    title: 'Without Icon',
    variant: 'ghost',
  },
];

const navigationLinks2: LinkProp[] = [
  {
    id: generateId('Social'),
    title: 'Social',
    label: '972',
    icon: 'lucide:user-2',
    variant: 'ghost',
  },
  {
    id: generateId('Updates'),
    title: 'Updates',
    label: '342',
    icon: 'lucide:alert-circle',
    variant: 'ghost',
  },
  {
    id: generateId('Forums'),
    title: 'Forums',
    label: '128',
    icon: 'lucide:message-square',
    variant: 'ghost',
  },
  {
    id: generateId('Shopping'),
    title: 'Shopping',
    label: '8',
    icon: 'lucide:shopping-cart',
    variant: 'ghost',
  },
  {
    id: generateId('Promotions'),
    title: 'Promotions',
    label: '21',
    icon: 'lucide:archive',
    variant: 'ghost',
  },
  {
    id: generateId('Test Embedded'),
    title: 'Test Embedded',
    label: '15',
    icon: 'lucide:archive',
    variant: 'ghost',
    sub: [
      {
        id: generateId('Children 1'),
        title: 'Children 1',
        label: '2',
        icon: 'lucide:shopping-cart',
        variant: 'ghost',
      },
      {
        id: generateId('Children 2'),
        title: 'Children 2',
        label: '22',
        icon: 'lucide:shopping-cart',
        variant: 'ghost',
        sub: [
          {
            id: generateId('Children 2 - Children 3'),
            title: 'Children 2 - Children 3',
            label: '22',
            icon: 'lucide:shopping-cart',
            variant: 'ghost',
          },
        ],
      },
    ],
  },
  {
    id: generateId('Another Test Embedded'),
    title: 'Another Test Embedded',
    icon: 'lucide:archive',
    variant: 'ghost',
    sub: [
      {
        id: generateId('Children 1'),
        title: 'Children 1',
        variant: 'ghost',
      },
      {
        id: generateId('Children 2'),
        title: 'Children 2',
        label: '22',
        icon: 'lucide:shopping-cart',
        variant: 'ghost',
        sub: [
          {
            id: generateId('Children 2 - Children 3'),
            title: 'Children 2 - Children 3',
            label: '🟢',
            icon: 'lucide:shopping-cart',
            variant: 'ghost',
          },
        ],
      },
    ],
  },
];

export { navigationLinks as links, navigationLinks2 as links2 };
