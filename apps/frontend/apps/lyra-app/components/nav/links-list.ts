export interface LinkProp {
  title: string;
  label?: string;
  to?: string;
  icon?: string;
  variant: 'default' | 'ghost';
  sub?: LinkProp[];
}

const links: LinkProp[] = [
  {
    title: 'Dashboard',
    to: '/',
    icon: 'lucide:layout-dashboard',
    variant: 'default',
  },
  {
    title: 'Audience',
    icon: 'ph:users',
    variant: 'ghost',
    sub: [
      {
        title: 'Subscribers',
        icon: 'lucide:user-2',
        to: '/subscribers',
        variant: 'ghost',
      },
      {
        title: 'Subscribe Forms',
        to: '/forms/subscribe',
        icon: 'material-symbols-light:dynamic-form-outline-rounded',
        variant: 'ghost',
      },
    ],
  },
  {
    title: 'Sent',
    label: '',
    icon: 'lucide:send',
    variant: 'ghost',
  },
  {
    title: 'Junk',
    label: '23',
    icon: 'lucide:archive',
    variant: 'ghost',
  },
  {
    title: 'Trash',
    label: '',
    icon: 'lucide:trash',
    variant: 'ghost',
  },
  {
    title: 'Archive',
    label: '',
    icon: 'lucide:archive',
    variant: 'ghost',
  },
  {
    title: 'Without Icon',
    variant: 'ghost',
  },
];

const links2: LinkProp[] = [
  {
    title: 'Social',
    label: '972',
    icon: 'lucide:user-2',
    variant: 'ghost',
  },
  {
    title: 'Updates',
    label: '342',
    icon: 'lucide:alert-circle',
    variant: 'ghost',
  },
  {
    title: 'Forums',
    label: '128',
    icon: 'lucide:message-square',
    variant: 'ghost',
  },
  {
    title: 'Shopping',
    label: '8',
    icon: 'lucide:shopping-cart',
    variant: 'ghost',
  },
  {
    title: 'Promotions',
    label: '21',
    icon: 'lucide:archive',
    variant: 'ghost',
  },
  {
    title: 'Test Embedded',
    label: '15',
    icon: 'lucide:archive',
    variant: 'ghost',
    sub: [
      {
        title: 'Children 1',
        label: '2',
        icon: 'lucide:shopping-cart',
        variant: 'ghost',
      },
      {
        title: 'Children 2',
        label: '22',
        icon: 'lucide:shopping-cart',
        variant: 'ghost',
        sub: [
          {
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
    title: 'Another Test Embedded',
    icon: 'lucide:archive',
    variant: 'ghost',
    sub: [
      {
        title: 'Children 1',
        variant: 'ghost',
      },
      {
        title: 'Children 2',
        label: '22',
        icon: 'lucide:shopping-cart',
        variant: 'ghost',
        sub: [
          {
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

export { links, links2 };
