import type { Command } from '@lyra/shared';

export default class DeleteFormCommand implements Command {
	constructor(public readonly id: string) {}
}
