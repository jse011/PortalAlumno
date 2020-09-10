package com.consultoraestrategia.ss_portalalumno.gadgets.justify;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

/* ***********************************************************************
Copyright 2014 CodesGood
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*********************************************************************** */

//Created by CodesGood on 7/12/14.ustifiedTextView es una vista de Android que justifica el texto. 🙌🏻
//
//Android introdujo en el nivel 26 de API el método setJustificationMode (int) ,
// que sirve para justificar el texto dentro de un TextView.
//
//Si su nivel mínimo de API es 26, recomiendo TextView.setJustificationMode (int) .
// Pero si necesita admitir versiones anteriores de Android, JustifiedTextView es lo que necesitaguiño

public class JustifiedTextView extends AppCompatTextView {

    //Hair space character that will fill the space among spaces.
    private final static String HAIR_SPACE = "\u200A";

    //Normal space character that will take place between words.
    private final static String NORMAL_SPACE = " ";

    //TextView's width.
    private int viewWidth;

    //Justified sentences in TextView's text.
    private List<String> sentences = new ArrayList<>();

    //Sentence being justified.
    private List<String> currentSentence = new ArrayList<>();

    //Sentence filled with spaces.
    private List<String> sentenceWithSpaces = new ArrayList<>();

    //String that will storage the text with the inserted spaces.
    private String justifiedText = "";

    //Object that generates random numbers, this is part of the justification algorithm.
    private Random random = new Random();

    //Default Constructors.
    public JustifiedTextView(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    public JustifiedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    public JustifiedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //This class won't repeat the process of justify text if it's already done.
        //super.onDraw(canvas);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.onDraw(canvas);
        }else {
            if (!justifiedText.equals(getText().toString())) {

                ViewGroup.LayoutParams params = getLayoutParams();
                String text = getText().toString();

                viewWidth = getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());

                //This class won't justify the text if the TextView has wrap_content as width
                //and won't justify the text if the view width is 0
                //AND! won't justify the text if it's empty.
                if (params.width != ViewGroup.LayoutParams.WRAP_CONTENT && viewWidth > 0 && !text.isEmpty()) {
                    justifiedText = getJustifiedText(text);

                    if (!justifiedText.isEmpty()) {
                        setText(justifiedText);
                        sentences.clear();
                        currentSentence.clear();
                    }
                } else {
                    super.onDraw(canvas);
                }
            } else {
                super.onDraw(canvas);
            }
        }

    }

    /**
     * Retrieves a String with appropriate spaces to justify the text in the TextView.
     *
     * @param text Text to be justified
     * @return Justified text
     */
    private String getJustifiedText(String text) {
        String[] words = text.split(NORMAL_SPACE);

        for (String word : words) {
            boolean containsNewLine = (word.contains("\n") || word.contains("\r"));

            if (fitsInSentence(word, currentSentence, true)) {
                addWord(word, containsNewLine);
            } else {
                sentences.add(fillSentenceWithSpaces(currentSentence));
                currentSentence.clear();
                addWord(word, containsNewLine);
            }
        }

        //Making sure we add the last sentence if needed.
        if (currentSentence.size() > 0) {
            sentences.add(getSentenceFromList(currentSentence, true));
        }

        //Returns the justified text.
        return getSentenceFromList(sentences, false);
    }

    /**
     * Adds a word into sentence and starts a new one if "new line" is part of the string.
     *
     * @param word            Word to be added
     * @param containsNewLine Specifies if the string contains a new line
     */
    private void addWord(String word, boolean containsNewLine) {
        currentSentence.add(word);
        if (containsNewLine) {
            sentences.add(getSentenceFromListCheckingNewLines(currentSentence));
            currentSentence.clear();
        }
    }

    /**
     * Creates a string using the words in the list and adds spaces between words if required.
     *
     * @param strings   Strings to be merged into one
     * @param addSpaces Specifies if the method should add spaces between words.
     * @return Returns a sentence using the words in the list.
     */
    private String getSentenceFromList(List<String> strings, boolean addSpaces) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string);

            if (addSpaces) {
                stringBuilder.append(NORMAL_SPACE);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Creates a string using the words in the list and adds spaces between words taking new lines
     * in consideration.
     *
     * @param strings Strings to be merged into one
     * @return Returns a sentence using the words in the list.
     */
    private String getSentenceFromListCheckingNewLines(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {
            stringBuilder.append(string);

            //We don't want to add a space next to the word if this one contains a new line character
            if (!string.contains("\n") && !string.contains("\r")) {
                stringBuilder.append(NORMAL_SPACE);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Fills sentence with appropriate amount of spaces.
     *
     * @param sentence Sentence we'll use to build the sentence with additional spaces
     * @return String with spaces.
     */
    private String fillSentenceWithSpaces(List<String> sentence) {
        sentenceWithSpaces.clear();

        //We don't need to do this process if the sentence received is a single word.
        if (sentence.size() > 1) {
            //We fill with normal spaces first, we can do this with confidence because "fitsInSentence"
            //already takes these spaces into account.
            for (String word : sentence) {
                sentenceWithSpaces.add(word);
                sentenceWithSpaces.add(NORMAL_SPACE);
            }

            //Filling sentence with thin spaces.
            while (fitsInSentence(HAIR_SPACE, sentenceWithSpaces, false)) {
                //We remove 2 from the sentence size because we need to make sure we are not adding
                //spaces to the end of the line.
                sentenceWithSpaces.add(getRandomNumber(sentenceWithSpaces.size() - 2), HAIR_SPACE);
            }
        }

        return getSentenceFromList(sentenceWithSpaces, false);
    }

    /**
     * Verifies if word to be added will fit into the sentence
     *
     * @param word      Word to be added
     * @param sentence  Sentence that will receive the new word
     * @param addSpaces Specifies weather we should add spaces to validation or not
     * @return True if word fits, false otherwise.
     */
    private boolean fitsInSentence(String word, List<String> sentence, boolean addSpaces) {
        String stringSentence = getSentenceFromList(sentence, addSpaces);
        stringSentence += word;

        float sentenceWidth = getPaint().measureText(stringSentence);

        return sentenceWidth < viewWidth;
    }

    /**
     * Returns a random number, it's part of the algorithm... don't blame me.
     *
     * @param max Max number in range
     * @return Random number.
     */
    private int getRandomNumber(int max) {
        //We add 1 to the result because we wanna prevent the logic from adding
        //spaces at the beginning of the sentence.
        return random.nextInt(max) + 1;
    }
}
