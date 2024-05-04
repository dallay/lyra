import type { Command } from '@lyra/shared';

export default class CreateFormCommand implements Command {
  constructor(
    public readonly id: string,
    public readonly name: string,
    public readonly header: string,
    public readonly description: string,
    public readonly inputPlaceholder: string,
    public readonly buttonText: string,
    public readonly buttonColor: string,
    public readonly backgroundColor: string,
    public readonly textColor: string,
    public readonly buttonTextColor: string
  ) {}
}
