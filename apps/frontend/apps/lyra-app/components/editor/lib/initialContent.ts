export const initialContent = `
<h1>
        This editor supports $\\\\LaTeX$ math expressions.
      </h1>
      <p>
        Did you know that $3 * 3 = 9$? Isn't that crazy? Also Pythagoras' theorem is $a^2 + b^2 = c^2$.<br />
        Also the square root of 2 is $\\\\sqrt{2}$. If you want to know more about $\\\\LaTeX$ visit <a href="https://katex.org/docs/supported.html" target="_blank">katex.org</a>.
      </p>
      <code>
        <pre>$\\\\LaTeX$</pre>
      </code>
      <p>
        Do you want go deeper? Here is a list of all supported functions:
      </p>
      <ul>
        <li>$\\\\sin(x)$</li>
        <li>$\\\\cos(x)$</li>
        <li>$\\\\tan(x)$</li>
        <li>$\\\\log(x)$</li>
        <li>$\\\\ln(x)$</li>
        <li>$\\\\sqrt{x}$</li>
        <li>$\\\\sum_{i=0}^n x_i$</li>
        <li>$\\\\int_a^b x^2 dx$</li>
        <li>$\\\\frac{1}{x}$</li>
        <li>$\\\\binom{n}{k}$</li>
        <li>$\\\\sqrt[n]{x}$</li>
        <li>$\\\\left(\\\\frac{1}{x}\\\\right)$</li>
        <li>$\\\\left\\\\{\\\\begin{matrix}x&\\\\text{if }x>0\\\\\\\\0&\\\\text{otherwise}\\\\end{matrix}\\\\right.$</li>
      </ul>
    <p>Look at these details</p>
          <details>
            <summary>This is a summary</summary>
            <p>Surprise!</p>
          </details>
          <p>Nested details are also supported</p>
          <details open>
            <summary>This is another summary</summary>
            <p>And there is even more.</p>
            <details>
              <summary>We need to go deeper</summary>
              <p>Booya!</p>
            </details>
          </details>

      <figure data-type="capturedImage">
          <figcaption>
            Image caption
          </figcaption>
          <img src="https://placehold.co/800x400/black/white" alt="Random photo of something" title="Who’s dat?">
        </figure>
        <p>Some text</p>
        <img src="https://placehold.co/800x400">
      <h3>
          Have you seen our tables? They are amazing!
        </h3>
        <ul>
          <li>Tables with rows, cells and headers (optional)</li>
          <li>Support for <code>colgroup</code> and <code>rowspan</code></li>
          <li>And even resizable columns (optional)</li>
        </ul>
        <p>
          Here is an example:
        </p>
        <table>
          <tr>
            <th>Company</th>
            <th>Contact</th>
            <th>Country</th>
          </tr>
          <tr>
            <td>Alfreds Futterkiste</td>
            <td>Maria Anders</td>
            <td>Germany</td>
          </tr>
          <tr>
            <td>Centro comercial Moctezuma</td>
            <td>Francisco Chang</td>
            <td>Mexico</td>
          </tr>
          <tr>
            <td>Ernst Handel</td>
            <td>Roland Mendel</td>
            <td>Austria</td>
          </tr>
          <tr>
            <td>Island Trading</td>
            <td>Helen Bennett</td>
            <td>UK</td>
          </tr>
          <tr>
            <td>Laughing Bacchus Winecellars</td>
            <td>Yoshi Tannamuri</td>
            <td>Canada</td>
          </tr>
          <tr>
            <td>Magazzini Alimentari Riuniti</td>
            <td>Giovanni Rovelli</td>
            <td>Italy</td>
          </tr>
        </table>


        <p>
          That's a boring paragraph followed by a fenced code block:
        </p>
        <pre><code class="language-javascript">for (var i=1; i <= 20; i++)
{
  if (i % 15 == 0)
    console.log("FizzBuzz");
  else if (i % 3 == 0)
    console.log("Fizz");
  else if (i % 5 == 0)
    console.log("Buzz");
  else
    console.log(i);
}</code></pre>
        <p>
          Press Command/Ctrl + Enter to leave the fenced code block and continue typing in boring paragraphs.
        </p>
        `;
