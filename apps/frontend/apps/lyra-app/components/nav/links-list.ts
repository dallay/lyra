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
    variant: 'ghost',
  },
  {
    id: generateId('Start Writing'),
    title: 'Start Writing',
    to: '/posts',
    icon: 'lucide:layout-dashboard',
    variant: 'ghost',
  },
  {
    id: generateId('Posts'),
    title: 'Posts',
    to: '/posts',
    icon: 'lucide:file-text',
    variant: 'ghost',
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
    id: generateId('Grow'),
    title: 'Grow',
    icon: 'lucide:trending-up',
    variant: 'ghost',
    sub: [
      {
        id: generateId('Referral Program'),
        title: 'Referral Program',
        icon: 'lucide:users',
        to: '/grow/referral-program',
        variant: 'ghost',
      },
      {
        id: generateId('Magic Links'),
        title: 'Magic Links',
        to: '/grow/magic-links',
        icon: 'lucide:link',
        variant: 'ghost',
      },
    ],
  },
  {
    id: generateId('Design'),
    title: 'Design',
    icon: 'lucide:paint-bucket',
    variant: 'ghost',
    sub: [
      {
        id: generateId('Website Builder'),
        title: 'Website Builder',
        to: '/design/website-builder',
        variant: 'ghost',
      },
      {
        id: generateId('Newsletter Builder'),
        title: 'Newsletter Builder',
        to: '/design/newsletter-builder',
        variant: 'ghost',
      },
    ],
  },
  {
    id: generateId('Analyze'),
    title: 'Analyze',
    icon: 'lucide:bar-chart',
    variant: 'ghost',
    sub: [
      {
        id: generateId('Subscribers Report'),
        title: 'Subscribers Report',
        to: '/analyze/subscribers-report',
        variant: 'ghost',
      },
      {
        id: generateId('Posts Report'),
        title: 'Posts Report',
        to: '/analyze/posts-report',
        variant: 'ghost',
      },
      {
        id: generateId('Clicks Report'),
        title: 'Clicks Report',
        to: '/analyze/clicks-report',
        variant: 'ghost',
      }
    ],
  },
];

export { navigationLinks  };
