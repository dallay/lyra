import type { Component } from "vue";

export interface LinkProp {
  id: string;
  title: string;
  label?: string;
  to?: string;
  icon?: string;
  variant: 'default' | 'ghost';
  sub?: LinkProp[];
}
